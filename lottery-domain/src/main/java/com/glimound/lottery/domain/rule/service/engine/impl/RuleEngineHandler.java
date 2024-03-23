package com.glimound.lottery.domain.rule.service.engine.impl;

import com.glimound.lottery.domain.rule.model.aggregates.TreeRuleRich;
import com.glimound.lottery.domain.rule.model.req.DecisionMatterReq;
import com.glimound.lottery.domain.rule.model.res.EngineRes;
import com.glimound.lottery.domain.rule.model.vo.TreeNodeVO;
import com.glimound.lottery.domain.rule.repository.IRuleRepository;
import com.glimound.lottery.domain.rule.service.engine.BaseEngine;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 规则引擎处理器
 * @author Glimound
 */
@Service("ruleEngineHandler")
public class RuleEngineHandler extends BaseEngine {

    @Resource
    private IRuleRepository ruleRepository;

    @Override
    public EngineRes process(DecisionMatterReq matter) {
        // 决策规则树
        TreeRuleRich treeRuleRich = ruleRepository.getTreeRuleRichById(matter.getTreeId());
        if (treeRuleRich == null) {
            throw new RuntimeException("Tree Rule is null!");
        }

        // 决策节点
        TreeNodeVO leafNodeInfo = engineDecisionMaker(treeRuleRich, matter);

        // 决策结果
        return new EngineRes(true, matter.getUserId(), leafNodeInfo.getTreeId(), leafNodeInfo.getTreeNodeId(), leafNodeInfo.getNodeValue());
    }
}
