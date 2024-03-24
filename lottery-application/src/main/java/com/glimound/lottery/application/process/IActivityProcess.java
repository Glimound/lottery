package com.glimound.lottery.application.process;

import com.glimound.lottery.application.process.res.DrawProcessRes;
import com.glimound.lottery.application.process.req.DrawProcessReq;
import com.glimound.lottery.application.process.res.RuleQuantificationCrowdRes;
import com.glimound.lottery.domain.rule.model.req.DecisionMatterReq;

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

    /**
     * 规则量化人群，返回可参与的活动ID
     * @param req   规则请求
     * @return      量化结果，用户可以参与的活动ID
     */
    RuleQuantificationCrowdRes doRuleQuantificationCrowd(DecisionMatterReq req);

}
