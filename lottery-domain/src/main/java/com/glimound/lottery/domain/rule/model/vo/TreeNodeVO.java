package com.glimound.lottery.domain.rule.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 规则树节点信息
 * @author Glimound
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TreeNodeVO {

    /** 规则树ID */
    private Long treeId;
    /** 规则树节点ID */
    private Long treeNodeId;
    /** 节点类型；1中间节点、2叶子节点 */
    private Integer nodeType;
    /** 节点值 */
    private String nodeValue;
    /** 规则Key */
    private String ruleKey;
    /** 规则描述 */
    private String ruleDesc;
    /** 节点链路 */
    private List<TreeNodeLineVO> treeNodeLineInfoList;

}
