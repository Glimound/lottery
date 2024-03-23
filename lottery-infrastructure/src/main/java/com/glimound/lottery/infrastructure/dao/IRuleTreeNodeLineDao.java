package com.glimound.lottery.infrastructure.dao;

import com.glimound.lottery.infrastructure.po.RuleTreeNodeLine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 规则树节点链路Dao
 * @author Glimound
 */
@Mapper
public interface IRuleTreeNodeLineDao {

    /**
     * 查询规则树节点链路集合
     * @param req   入参
     * @return      规则树节点连线集合
     */
    List<RuleTreeNodeLine> listRuleTreeNodeLine(RuleTreeNodeLine req);

    /**
     * 查询规则树链路数量
     *
     * @param treeId    规则树ID
     * @return          规则树连线数量
     */
    int getTreeNodeLineCountById(Long treeId);

}
