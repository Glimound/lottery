package com.glimound.lottery.infrastructure.repository;

import com.glimound.lottery.common.Constants;
import com.glimound.lottery.common.Result;
import com.glimound.lottery.domain.activity.model.req.PartakeReq;
import com.glimound.lottery.domain.activity.model.res.StockRes;
import com.glimound.lottery.domain.activity.model.vo.*;
import com.glimound.lottery.domain.activity.repository.IActivityRepository;
import com.glimound.lottery.infrastructure.dao.*;
import com.glimound.lottery.infrastructure.po.*;
import com.glimound.lottery.infrastructure.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * activity数据访问接口的实现
 * @author Glimound
 */
@Repository
@Slf4j
public class ActivityRepository implements IActivityRepository {

    @Resource
    private IActivityDao activityDao;
    @Resource
    private IAwardDao awardDao;
    @Resource
    private IStrategyDao strategyDao;
    @Resource
    private IStrategyDetailDao strategyDetailDao;
    @Resource
    private IUserTakeActivityCountDao userTakeActivityCountDao;
    @Resource
    private RedisUtil redisUtil;


    @Override
    public void addActivity(ActivityVO activity) {
        Activity req = new Activity();
        BeanUtils.copyProperties(activity, req);
        activityDao.insertActivity(req);
        // 若时间有效且该活动添加时已处于开启状态，设置活动库存key
        if (activity.getBeginDateTime().before(new Date()) && activity.getEndDateTime().after(new Date()) &&
                activity.getState().equals(Constants.ActivityState.DOING.getCode())) {
            redisUtil.set(Constants.RedisKey.KEY_LOTTERY_ACTIVITY_STOCK_COUNT(activity.getActivityId()),
                    activity.getStockCount(), activity.getEndDateTime().getTime() - new Date().getTime());
        }
    }

    @Override
    public void addAward(List<AwardVO> awardList) {
        List<Award> req = new ArrayList<>();
        for (AwardVO awardVO : awardList) {
            Award award = new Award();
            BeanUtils.copyProperties(awardVO, award);
            req.add(award);
        }
        awardDao.insertAwardList(req);
    }

    @Override
    public void addStrategy(StrategyVO strategy) {
        Strategy req = new Strategy();
        BeanUtils.copyProperties(strategy, req);
        strategyDao.insertStrategy(req);
    }

    @Override
    public void addStrategyDetailList(List<StrategyDetailVO> strategyDetailList) {
        List<StrategyDetail> req = new ArrayList<>();
        for (StrategyDetailVO strategyDetailVO : strategyDetailList) {
            StrategyDetail strategyDetail = new StrategyDetail();
            BeanUtils.copyProperties(strategyDetailVO, strategyDetail);
            req.add(strategyDetail);
        }
        strategyDetailDao.insertStrategyDetailList(req);
    }

    @Override
    public boolean alterStatus(Long activityId, Enum<Constants.ActivityState> beforeState, Enum<Constants.ActivityState> afterState) {
        AlterStateVO alterStateVO = new AlterStateVO(activityId,((Constants.ActivityState) beforeState).getCode(),
                ((Constants.ActivityState) afterState).getCode());
        int count = activityDao.alterState(alterStateVO);
        return count == 1;
    }

    @Override
    public void addStockCountToRedis(Long activityId) {
        Activity activity = activityDao.getActivityById(activityId);
        redisUtil.set(Constants.RedisKey.KEY_LOTTERY_ACTIVITY_STOCK_COUNT(activityId), activity.getStockCount(),
                activity.getEndDateTime().getTime() - new Date().getTime());
    }

    @Override
    public ActivityBillVO getActivityBill(PartakeReq req) {
        // 查询活动信息
        Activity activity = activityDao.getActivityById(req.getActivityId());

        // 查询领取次数
        UserTakeActivityCount userTakeActivityCountReq = new UserTakeActivityCount();
        userTakeActivityCountReq.setUId(req.getUId());
        userTakeActivityCountReq.setActivityId(req.getActivityId());
        UserTakeActivityCount userTakeActivityCount = userTakeActivityCountDao.getUserTakeActivityCount(userTakeActivityCountReq);

        // 封装结果信息
        ActivityBillVO activityBillVO = new ActivityBillVO();
        BeanUtils.copyProperties(req, activityBillVO);
        BeanUtils.copyProperties(activity, activityBillVO);
        activityBillVO.setUserTakeLeftCount(userTakeActivityCount == null ? null : userTakeActivityCount.getLeftCount());

        return activityBillVO;
    }

    @Override
    public int deductActivityStockById(Long activityId) {
        return activityDao.deductActivityStockById(activityId);
    }

    @Override
    public List<ActivityVO> listToDoActivity(Long id) {
        List<Activity> activityList = activityDao.listToDoActivity(id);
        List<ActivityVO> activityVOList = new ArrayList<>(activityList.size());
        for (Activity activity : activityList) {
            ActivityVO activityVO = new ActivityVO();
            BeanUtils.copyProperties(activity, activityVO);
            activityVOList.add(activityVO);
        }
        return activityVOList;
    }

    @Override
    public StockRes deductActivityStockByRedis(String uId, Long activityId) {
        // 获取抽奖活动库存 Key
        String stockKey = Constants.RedisKey.KEY_LOTTERY_ACTIVITY_STOCK_COUNT(activityId);
        // 扣减库存
        int deductedStockCount = (int) redisUtil.decr(stockKey, 1);
        // 若库存不足则恢复库存
        if (deductedStockCount < 0) {
            redisUtil.incr(stockKey, 1);
            return new StockRes(Constants.ResponseCode.OUT_OF_STOCK.getCode(), Constants.ResponseCode.OUT_OF_STOCK.getInfo());
        }
        return new StockRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo(), deductedStockCount);
    }

    @Override
    public void recoverActivityStockByRedis(Long activityId) {
        String stockKey = Constants.RedisKey.KEY_LOTTERY_ACTIVITY_STOCK_COUNT(activityId);
        redisUtil.incr(stockKey, 1);
    }

    @Override
    public Integer getActivityStockByRedis(Long activityId) {
        String stockKey = Constants.RedisKey.KEY_LOTTERY_ACTIVITY_STOCK_COUNT(activityId);
        return (Integer) redisUtil.get(stockKey);
    }

}
