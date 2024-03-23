package com.glimound.lottery.domain.rule.service.engine;

import com.glimound.lottery.common.Constants;
import com.glimound.lottery.domain.rule.model.aggregates.TreeRuleRich;
import com.glimound.lottery.domain.rule.model.req.DecisionMatterReq;
import com.glimound.lottery.domain.rule.model.res.EngineRes;
import com.glimound.lottery.domain.rule.model.vo.TreeNodeVO;
import com.glimound.lottery.domain.rule.model.vo.TreeRootVO;
import com.glimound.lottery.domain.rule.service.logic.ILogicFilter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 规则引擎基础抽象类
 * @author Glimound
 */
@Slf4j
public abstract class BaseEngine extends EngineConfig implements IEngineFilter {

    @Override
    public EngineRes process(DecisionMatterReq matter) {
        throw new RuntimeException("未实现规则引擎服务");
    }

    protected TreeNodeVO engineDecisionMaker(TreeRuleRich treeRuleRich, DecisionMatterReq matter) {
        TreeRootVO treeRoot = treeRuleRich.getTreeRoot();
        Map<Long, TreeNodeVO> treeNodeMap = treeRuleRich.getTreeNodeMap();

        // 规则树根ID
        Long rootNodeId = treeRoot.getTreeRootNodeId();
        TreeNodeVO treeNodeInfo = treeNodeMap.get(rootNodeId);

        // 节点类型[NodeType]；1中间节点、2叶子节点
        while (Constants.NodeType.INTERNAL.equals(treeNodeInfo.getNodeType())) {
            String ruleKey = treeNodeInfo.getRuleKey();
            ILogicFilter logicFilter = logicFilterMap.get(ruleKey);
            String matterValue = logicFilter.matterValue(matter);
            Long nextNode = logicFilter.filter(matterValue, treeNodeInfo.getTreeNodeLineInfoList());
            treeNodeInfo = treeNodeMap.get(nextNode);
            log.info("决策树引擎=>{} userId：{} treeId：{} treeNode：{} ruleKey：{} matterValue：{}", treeRoot.getTreeName(),
                    matter.getUserId(), matter.getTreeId(), treeNodeInfo.getTreeNodeId(), ruleKey, matterValue);
        }

        return treeNodeInfo;
    }
}
