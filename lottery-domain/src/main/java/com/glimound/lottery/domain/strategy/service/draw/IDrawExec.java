package com.glimound.lottery.domain.strategy.service.draw;

import com.glimound.lottery.domain.strategy.model.req.DrawReq;
import com.glimound.lottery.domain.strategy.model.res.DrawRes;

/**
 * 抽奖执行接口
 * @author Glimound
 */
public interface IDrawExec {
    DrawRes doDrawExec(DrawReq req);
}
