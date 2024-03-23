package com.glimound.lottery.domain.strategy.repository;

import com.glimound.lottery.domain.strategy.model.aggregate.StrategyRich;
import com.glimound.lottery.domain.strategy.model.vo.AwardBriefVO;

import java.util.List;

/**
 * @author Glimound
 */
public interface IStrategyRepository {

    /**
     * 查询策略信息
     *
     * @param strategyId 策略ID
     * @return           策略信息
     */
    StrategyRich getStrategyRichById(Long strategyId);

    /**
     * 查询奖励配置
     *
     * @param awardId   奖励ID
     * @return          奖励信息
     */
    AwardBriefVO getAwardById(Long awardId);

    /**
     * 查询无库存奖品
     *
     * @param strategyId 策略ID
     * @return           无库存奖品
     */
    List<Long> listNoStockStrategyAwardById(Long strategyId);

    /**
     * 扣减库存
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return           扣减结果
     */
    boolean deductStockById(Long strategyId, Long awardId);
}
