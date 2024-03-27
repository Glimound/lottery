package com.glimound.lottery.infrastructure.repository;

import com.glimound.lottery.common.Constants;
import com.glimound.lottery.domain.activity.model.vo.ActivityPartakeRecordVO;
import com.glimound.lottery.domain.activity.model.vo.DrawOrderVO;
import com.glimound.lottery.domain.activity.model.vo.InvoiceVO;
import com.glimound.lottery.domain.activity.model.vo.UserTakeActivityVO;
import com.glimound.lottery.domain.activity.repository.IUserTakeActivityRepository;
import com.glimound.lottery.infrastructure.dao.IActivityDao;
import com.glimound.lottery.infrastructure.dao.IUserStrategyExportDao;
import com.glimound.lottery.infrastructure.dao.IUserTakeActivityCountDao;
import com.glimound.lottery.infrastructure.dao.IUserTakeActivityDao;
import com.glimound.lottery.infrastructure.po.Activity;
import com.glimound.lottery.infrastructure.po.UserStrategyExport;
import com.glimound.lottery.infrastructure.po.UserTakeActivity;
import com.glimound.lottery.infrastructure.po.UserTakeActivityCount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户参与活动仓储
 * @author Glimound
 */
@Repository
public class UserTakeActivityRepository implements IUserTakeActivityRepository {

    @Resource
    private IUserTakeActivityCountDao userTakeActivityCountDao;
    @Resource
    private IUserTakeActivityDao userTakeActivityDao;
    @Resource
    private IUserStrategyExportDao userStrategyExportDao;
    @Resource
    private IActivityDao activityDao;

    @Override
    public int deductLeftCount(Long activityId, String activityName, Integer takeCount, Integer userTakeLeftCount, String uId) {
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
        userStrategyExport.setMqState(Constants.MQState.INIT.getCode());
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
    @Override
    public void updateMqState(String uId, Long orderId, Integer mqState) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setUId(uId);
        userStrategyExport.setOrderId(orderId);
        userStrategyExport.setMqState(mqState);
        userStrategyExportDao.updateMqState(userStrategyExport);
    }

    @Override
    public List<InvoiceVO> listFailureMqState() {
        // 查询发送MQ失败和超时10秒未发送MQ的数据
        List<UserStrategyExport> userStrategyExportList = userStrategyExportDao.listFailureMqState();
        // 转换对象
        List<InvoiceVO> invoiceVOList = new ArrayList<>(userStrategyExportList.size());
        for (UserStrategyExport userStrategyExport : userStrategyExportList) {
            InvoiceVO invoiceVO = new InvoiceVO();
            BeanUtils.copyProperties(userStrategyExport, invoiceVO);
            invoiceVOList.add(invoiceVO);
        }
        return invoiceVOList;
    }

    @Override
    public void updateActivityStock(ActivityPartakeRecordVO activityPartakeRecordVO) {
        Activity activity = new Activity();
        activity.setActivityId(activityPartakeRecordVO.getActivityId());
        activity.setStockSurplusCount(activityPartakeRecordVO.getStockSurplusCount());
        activityDao.updateActivityStock(activity);
    }

}
