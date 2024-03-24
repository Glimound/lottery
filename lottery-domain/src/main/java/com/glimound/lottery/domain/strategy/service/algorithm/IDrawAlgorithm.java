package com.glimound.lottery.domain.strategy.service.algorithm;

import com.glimound.lottery.domain.strategy.model.vo.AwardRateVO;

import java.util.List;

/**
 * 抽奖算法接口
 * @author glimound
 */
public interface IDrawAlgorithm {

    /**
     * 初始化奖品概率配置集合
     * @param strategyId 策略ID
     * @param awardRateInfoList 奖品概率配置集合，保存奖品id及其中奖概率
     */
    void initAwardRateInfoMap(Long strategyId, List<AwardRateVO> awardRateInfoList);

    /**
     * 初始化算法所需的内部数据结构，若已存在则无需重复初始化
     * @param strategyId 策略ID
     * @param awardRateInfoList 奖品概率配置集合，保存奖品id及其中奖概率
     */
    void initializeIfAbsent(Long strategyId, List<AwardRateVO> awardRateInfoList);

    /**
     * 随机抽奖
     *
     * @param strategyId 策略ID
     * @param excludeAwardIds 排除掉已经不能作为抽奖的奖品ID，留给风控和空库存使用
     * @return 中奖结果：奖品id
     */
    Long randomDraw(Long strategyId, List<Long> excludeAwardIds);

}
