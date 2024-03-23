package com.glimound.lottery.infrastructure.dao;

import com.glimound.lottery.infrastructure.po.RuleTree;
import org.apache.ibatis.annotations.Mapper;

/**
 * 规则树配置Dao
 * @author Glimound
 */
@Mapper
public interface IRuleTreeDao {

    /**
     * 规则树查询
     * @param id ID
     * @return   规则树
     */
    RuleTree getRuleTreeById(Long id);

    /**
     * 规则树简要信息查询
     * @param treeId 规则树ID
     * @return       规则树
     */
    RuleTree getBriefRuleTree(Long treeId);

}
