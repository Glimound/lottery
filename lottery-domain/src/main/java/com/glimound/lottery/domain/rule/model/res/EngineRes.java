package com.glimound.lottery.domain.rule.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 决策结果
 * @author Glimound
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EngineRes {

    /** 执行结果 */
    private boolean success;
    /** 用户ID */
    private String userId;
    /** 规则树ID */
    private Long treeId;
    /** 叶子节点ID */
    private Long leafNodeId;
    /** 叶子节点值 */
    private String leafNodeValue;

}
