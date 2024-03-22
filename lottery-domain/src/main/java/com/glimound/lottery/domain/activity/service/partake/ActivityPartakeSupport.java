package com.glimound.lottery.domain.activity.service.partake;

import com.glimound.lottery.domain.activity.model.req.PartakeReq;
import com.glimound.lottery.domain.activity.model.vo.ActivityBillVO;
import com.glimound.lottery.domain.activity.repository.IActivityRepository;

import javax.annotation.Resource;

/**
 * 为活动领取操作提供的通用数据服务
 * @author Glimound
 */
public class ActivityPartakeSupport {
    @Resource
    protected IActivityRepository activityRepository;

    protected ActivityBillVO getActivityBill(PartakeReq req){
        return activityRepository.getActivityBill(req);
    }
}
