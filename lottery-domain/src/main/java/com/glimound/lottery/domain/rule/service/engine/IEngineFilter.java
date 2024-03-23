package com.glimound.lottery.domain.rule.service.engine;

import com.glimound.lottery.domain.rule.model.req.DecisionMatterReq;
import com.glimound.lottery.domain.rule.model.res.EngineRes;

/**
 * 规则过滤器引擎接口
 * @author Glimound
 */
public interface IEngineFilter {

    /**
     * 规则过滤器接口
     *
     * @param matter      规则决策物料
     * @return            规则决策结果
     */
    EngineRes process(final DecisionMatterReq matter);
}
