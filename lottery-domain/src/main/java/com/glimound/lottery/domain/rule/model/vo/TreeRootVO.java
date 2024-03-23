package com.glimound.lottery.domain.rule.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 规则树根配置
 * @author Glimound
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TreeRootVO {

    /** 规则树ID */
    private Long treeId;
    /** 规则树根ID */
    private Long treeRootNodeId;
    /** 规则树名称 */
    private String treeName;

}
