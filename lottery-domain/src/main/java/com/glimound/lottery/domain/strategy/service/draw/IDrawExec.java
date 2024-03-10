package com.glimound.lottery.domain.strategy.service.draw;

import com.glimound.lottery.domain.strategy.model.req.DrawReq;
import com.glimound.lottery.domain.strategy.model.res.DrawRes;

/**
 * 抽奖执行接口
 * @author Glimound
 */
public interface IDrawExec {

    /**
     * 抽奖方法
     * @param req 抽奖参数；用户ID、策略ID
     * @return    中奖结果
     */
    DrawRes doDrawExec(DrawReq req);
}
