package com.glimound.lottery.domain.strategy.model.aggregate;

import com.glimound.lottery.domain.strategy.model.vo.StrategyBriefVO;
import com.glimound.lottery.domain.strategy.model.vo.StrategyDetailBriefVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Glimound
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StrategyRich {

    /**
     * 策略ID
     */
    private Long strategyId;

    /**
     * 策略配置
     */
    private StrategyBriefVO strategy;

    /**
     * 策略明细
     */
    private List<StrategyDetailBriefVO> strategyDetailList;

}
