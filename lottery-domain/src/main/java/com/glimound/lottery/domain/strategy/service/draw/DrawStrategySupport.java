package com.glimound.lottery.domain.strategy.service.draw;

import com.glimound.lottery.domain.strategy.model.aggregate.StrategyRich;
import com.glimound.lottery.domain.strategy.model.vo.AwardBriefVO;
import com.glimound.lottery.domain.strategy.repository.IStrategyRepository;

import javax.annotation.Resource;

/**
 * 提供通用的数据服务
 * @author Glimound
 */
public abstract class DrawStrategySupport extends DrawConfig {
    @Resource
    protected IStrategyRepository strategyRepository;

    /**
     * 查询策略配置信息
     *
     * @param strategyId 策略ID
     * @return 策略配置信息
     */
    protected StrategyRich getStrategyRichById(Long strategyId){
        return strategyRepository.getStrategyRichById(strategyId);
    }

    /**
     * 查询奖品详情信息
     *
     * @param awardId 奖品ID
     * @return 中奖详情
     */
    protected AwardBriefVO getAwardById(Long awardId){
        return strategyRepository.getAwardById(awardId);
    }
}
