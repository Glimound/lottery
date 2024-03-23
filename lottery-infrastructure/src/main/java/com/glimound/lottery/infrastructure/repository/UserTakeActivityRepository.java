package com.glimound.lottery.infrastructure.repository;

import com.glimound.lottery.common.Constants;
import com.glimound.lottery.domain.activity.model.vo.DrawOrderVO;
import com.glimound.lottery.domain.activity.model.vo.UserTakeActivityVO;
import com.glimound.lottery.domain.activity.repository.IUserTakeActivityRepository;
import com.glimound.lottery.infrastructure.dao.IUserStrategyExportDao;
import com.glimound.lottery.infrastructure.dao.IUserTakeActivityCountDao;
import com.glimound.lottery.infrastructure.dao.IUserTakeActivityDao;
import com.glimound.lottery.infrastructure.po.UserStrategyExport;
import com.glimound.lottery.infrastructure.po.UserTakeActivity;
import com.glimound.lottery.infrastructure.po.UserTakeActivityCount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户参与活动仓储
 * @author Glimound
 */
@Component
public class UserTakeActivityRepository implements IUserTakeActivityRepository {

    @Resource
    private IUserTakeActivityCountDao userTakeActivityCountDao;
    @Resource
    private IUserTakeActivityDao userTakeActivityDao;
    @Resource
    private IUserStrategyExportDao userStrategyExportDao;

    @Override
    public int deductLeftCount(Long activityId, String activityName, Integer takeCount, Integer userTakeLeftCount, String uId, Date partakeDate) {
        if (userTakeLeftCount == null) {
            UserTakeActivityCount userTakeActivityCount = new UserTakeActivityCount();
            userTakeActivityCount.setUId(uId);
            userTakeActivityCount.setActivityId(activityId);
            userTakeActivityCount.setTotalCount(takeCount);
            userTakeActivityCount.setLeftCount(takeCount - 1);
            userTakeActivityCountDao.insertUserTakeActivityCount(userTakeActivityCount);
            return 1;
        } else {
            UserTakeActivityCount userTakeActivityCount = new UserTakeActivityCount();
            userTakeActivityCount.setUId(uId);
            userTakeActivityCount.setActivityId(activityId);
            return userTakeActivityCountDao.updateLeftCount(userTakeActivityCount);
        }
    }

    @Override
    public void takeActivity(Long activityId, String activityName, Long strategyId, Integer takeCount, Integer userTakeLeftCount, String uId, Date takeDate, Long takeId) {
        UserTakeActivity userTakeActivity = new UserTakeActivity();
        userTakeActivity.setUId(uId);
        userTakeActivity.setTakeId(takeId);
        userTakeActivity.setActivityId(activityId);
        userTakeActivity.setActivityName(activityName);
        userTakeActivity.setTakeDate(takeDate);
        userTakeActivity.setStrategyId(strategyId);
        userTakeActivity.setState(Constants.TaskState.NO_USED.getCode());

        if (userTakeLeftCount == null) {
            userTakeActivity.setTakeCount(1);
        } else {
            userTakeActivity.setTakeCount(takeCount - userTakeLeftCount + 1);
        }
        String uuid = uId + "_" + activityId + "_" + userTakeActivity.getTakeCount();
        userTakeActivity.setUuid(uuid);

        userTakeActivityDao.insertUserTakeActivity(userTakeActivity);
    }

    @Override
    public int lockTakeActivity(String uId, Long activityId, Long takeId) {
        UserTakeActivity userTakeActivity = new UserTakeActivity();
        userTakeActivity.setUId(uId);
        userTakeActivity.setActivityId(activityId);
        userTakeActivity.setTakeId(takeId);
        return userTakeActivityDao.lockTakeActivity(userTakeActivity);
    }

    @Override
    public void saveUserStrategyExport(DrawOrderVO drawOrder) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        BeanUtils.copyProperties(drawOrder, userStrategyExport);
        userStrategyExport.setUuid(String.valueOf(drawOrder.getOrderId()));
        userStrategyExportDao.insertUserStrategyExport(userStrategyExport);
    }

    @Override
    public UserTakeActivityVO getNoConsumedTakeActivityOrder(Long activityId, String uId) {

        UserTakeActivity userTakeActivity = new UserTakeActivity();
        userTakeActivity.setUId(uId);
        userTakeActivity.setActivityId(activityId);
        UserTakeActivity noConsumedTakeActivityOrder = userTakeActivityDao.getNoConsumedTakeActivityOrder(userTakeActivity);

        // 未查询到符合的领取单，直接返回 NULL
        if (noConsumedTakeActivityOrder == null) {
            return null;
        }

        UserTakeActivityVO userTakeActivityVO = new UserTakeActivityVO();
        BeanUtils.copyProperties(noConsumedTakeActivityOrder, userTakeActivityVO);

        return userTakeActivityVO;
    }
}
