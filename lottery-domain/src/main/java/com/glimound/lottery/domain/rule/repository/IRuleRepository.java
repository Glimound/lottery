package com.glimound.lottery.domain.rule.repository;

import com.glimound.lottery.domain.rule.model.aggregates.TreeRuleRich;

/**
 * 规则信息仓储服务接口
 * @author Glimound
 */
public interface IRuleRepository {

    /**
     * 查询规则决策树配置
     *
     * @param treeId    决策树ID
     * @return          决策树配置
     */
    TreeRuleRich getTreeRuleRichById(Long treeId);
}
