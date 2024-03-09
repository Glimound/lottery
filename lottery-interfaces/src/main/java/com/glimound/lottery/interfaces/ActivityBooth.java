package com.glimound.lottery.interfaces;

import com.glimound.lottery.common.Constants;
import com.glimound.lottery.common.Result;
import com.glimound.lottery.infrastructure.dao.IActivityDao;
import com.glimound.lottery.infrastructure.po.Activity;
import com.glimound.lottery.rpc.IActivityBooth;
import com.glimound.lottery.rpc.dto.ActivityDto;
import com.glimound.lottery.rpc.req.ActivityReq;
import com.glimound.lottery.rpc.res.ActivityRes;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;

/**
 * 活动展台
 * @author Glimound
 */
@Service
public class ActivityBooth implements IActivityBooth {
    @Resource
    private IActivityDao activityDao;

    @Override
    public ActivityRes getActivityById(ActivityReq req) {
        Activity activity = activityDao.getActivityById(req.getActivityId());

        ActivityDto activityDto = new ActivityDto();
        if (activity != null) {
            BeanUtils.copyProperties(activity, activityDto);
        }

        Constants.ResponseCode code = Constants.ResponseCode.SUCCESS;
        return new ActivityRes(new Result(code.getCode(), code.getInfo()), activityDto);
    }
}
