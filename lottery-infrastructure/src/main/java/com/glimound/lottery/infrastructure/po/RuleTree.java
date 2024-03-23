package com.glimound.lottery.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 规则树
 * @author Glimound
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RuleTree {

    /** 主键ID */
    private Long id;
    /** 规则树名称 */
    private String treeName;
    /** 规则树描述 */
    private String treeDesc;
    /** 规则树根ID */
    private Long treeRootNodeId;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

}
