package com.glimound.lottery.domain.rule.model.aggregates;

import com.glimound.lottery.domain.rule.model.vo.TreeNodeVO;
import com.glimound.lottery.domain.rule.model.vo.TreeRootVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 规则树聚合对象
 * @author Glimound
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TreeRuleRich {
    /** 树根信息 */
    private TreeRootVO treeRoot;
    /** 树节点ID -> 子节点 */
    private Map<Long, TreeNodeVO> treeNodeMap;
}
