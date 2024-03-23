package com.glimound.lottery.infrastructure.repository;

import com.glimound.lottery.common.Constants;
import com.glimound.lottery.domain.rule.model.aggregates.TreeRuleRich;
import com.glimound.lottery.domain.rule.model.vo.TreeNodeLineVO;
import com.glimound.lottery.domain.rule.model.vo.TreeNodeVO;
import com.glimound.lottery.domain.rule.model.vo.TreeRootVO;
import com.glimound.lottery.domain.rule.repository.IRuleRepository;
import com.glimound.lottery.infrastructure.dao.IRuleTreeDao;
import com.glimound.lottery.infrastructure.dao.IRuleTreeNodeDao;
import com.glimound.lottery.infrastructure.dao.IRuleTreeNodeLineDao;
import com.glimound.lottery.infrastructure.po.RuleTree;
import com.glimound.lottery.infrastructure.po.RuleTreeNode;
import com.glimound.lottery.infrastructure.po.RuleTreeNodeLine;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 规则信息仓储服务
 * @author Glimound
 */
@Repository
public class RuleRepository implements IRuleRepository {

    @Resource
    private IRuleTreeDao ruleTreeDao;
    @Resource
    private IRuleTreeNodeDao ruleTreeNodeDao;
    @Resource
    private IRuleTreeNodeLineDao ruleTreeNodeLineDao;

    @Override
    public TreeRuleRich getTreeRuleRichById(Long treeId) {
        // 规则树
        RuleTree ruleTree = ruleTreeDao.getRuleTreeById(treeId);
        TreeRootVO treeRoot = new TreeRootVO();
        BeanUtils.copyProperties(ruleTree, treeRoot);
        treeRoot.setTreeId(ruleTree.getId());

        // 树节点->树连接线
        Map<Long, TreeNodeVO> treeNodeMap = new HashMap<>();
        List<RuleTreeNode> ruleTreeNodeList = ruleTreeNodeDao.listRuleTreeNodeById(treeId);
        // 遍历树节点
        for (RuleTreeNode treeNode : ruleTreeNodeList) {
            List<TreeNodeLineVO> treeNodeLineInfoList = new ArrayList<>();
            if (Constants.NodeType.INTERNAL.equals(treeNode.getNodeType())) {

                RuleTreeNodeLine ruleTreeNodeLineReq = new RuleTreeNodeLine();
                ruleTreeNodeLineReq.setTreeId(treeId);
                ruleTreeNodeLineReq.setNodeIdFrom(treeNode.getId());
                List<RuleTreeNodeLine> ruleTreeNodeLineList = ruleTreeNodeLineDao.listRuleTreeNodeLine(ruleTreeNodeLineReq);

                // 遍历该节点的每条边
                for (RuleTreeNodeLine nodeLine : ruleTreeNodeLineList) {
                    TreeNodeLineVO treeNodeLineInfo = new TreeNodeLineVO();
                    BeanUtils.copyProperties(nodeLine, treeNodeLineInfo);
                    treeNodeLineInfoList.add(treeNodeLineInfo);
                }
            }
            TreeNodeVO treeNodeInfo = new TreeNodeVO();
            BeanUtils.copyProperties(treeNode, treeNodeInfo);
            treeNodeInfo.setTreeNodeLineInfoList(treeNodeLineInfoList);
            treeNodeInfo.setTreeNodeId(treeNode.getId());
            treeNodeMap.put(treeNode.getId(), treeNodeInfo);
        }

        TreeRuleRich treeRuleRich = new TreeRuleRich();
        treeRuleRich.setTreeRoot(treeRoot);
        treeRuleRich.setTreeNodeMap(treeNodeMap);

        return treeRuleRich;
    }
}
