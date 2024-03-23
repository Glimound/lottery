package com.glimound.lottery.application.process;

import com.glimound.lottery.application.process.res.DrawProcessRes;
import com.glimound.lottery.application.process.req.DrawProcessReq;

/**
 * 活动抽奖流程编排接口
 * @author Glimound
 */
public interface IActivityProcess {

    /**
     * 执行抽奖流程
     * @param req 抽奖请求
     * @return    抽奖结果
     */
    DrawProcessRes doDrawProcess(DrawProcessReq req);
}
