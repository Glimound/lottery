package com.glimound.lottery.infrastructure.dao;

import com.glimound.lottery.infrastructure.po.RuleTreeNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 规则树节点Dao
 * @author Glimound
 */
@Mapper
public interface IRuleTreeNodeDao {

    /**
     * 查询规则树节点
     * @param treeId    规则树ID
     * @return          规则树节点集合
     */
    List<RuleTreeNode> listRuleTreeNodeById(Long treeId);

    /**
     * 查询规则树节点数量
     * @param treeId    规则树ID
     * @return          节点数量
     */
    int getRuleTreeNodeCountById(Long treeId);

    /**
     * 查询规则树中的规则
     *
     * @param treeId    规则树ID
     * @return          节点集合
     */
    List<RuleTreeNode> listRuleById(Long treeId);

}
