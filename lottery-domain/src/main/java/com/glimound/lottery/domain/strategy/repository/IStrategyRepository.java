package com.glimound.lottery.domain.strategy.repository;

import com.glimound.lottery.domain.strategy.model.aggregate.StrategyRich;
import com.glimound.lottery.infrastructure.po.Award;

/**
 * @author Glimound
 */
public interface IStrategyRepository {
    StrategyRich getStrategyRichById(Long strategyId);

    Award getAwardById(Long awardId);
}
