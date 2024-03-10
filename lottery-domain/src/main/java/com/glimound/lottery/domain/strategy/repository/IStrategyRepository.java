package com.glimound.lottery.domain.strategy.repository;

import com.glimound.lottery.domain.strategy.model.aggregate.StrategyRich;
import com.glimound.lottery.infrastructure.po.Award;

import java.util.List;

/**
 * @author Glimound
 */
public interface IStrategyRepository {
    StrategyRich getStrategyRichById(Long strategyId);

    Award getAwardById(Long awardId);

    List<Long> listNoStockStrategyAwardById(Long strategyId);

    /**
     * 扣减库存
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return           扣减结果
     */
    boolean deductStockById(Long strategyId, Long awardId);
}
