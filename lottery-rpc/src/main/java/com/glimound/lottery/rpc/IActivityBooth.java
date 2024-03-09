package com.glimound.lottery.rpc;

import com.glimound.lottery.rpc.req.ActivityReq;
import com.glimound.lottery.rpc.res.ActivityRes;

/**
 * 活动展台（创建、更新、查询）接口
 * @author Glimound
 */
public interface IActivityBooth {
    ActivityRes getActivityById(ActivityReq req);
}
