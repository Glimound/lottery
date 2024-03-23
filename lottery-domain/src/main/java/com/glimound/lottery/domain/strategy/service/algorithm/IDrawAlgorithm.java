package com.glimound.lottery.domain.strategy.service.algorithm;

import com.glimound.lottery.domain.strategy.model.vo.AwardRateVO;

import java.util.List;

/**
 * 抽奖算法接口
 * @author glimound
 */
public interface IDrawAlgorithm {

    /**
     * 初始化概率元祖（仅当strategyMode为1，即采用单项概率时需要） <br>
     * 对应中奖区间内的key经过斐波那契散列运算后的值，作为概率元组的key <br>
     * 相应的奖品id则作为该key所对应的value <br>
     * eg：1~30为一等奖，31~100为二等奖，则对1~30依次进行斐波那契散列运算，key为运算后结果，value为奖品id <br>
     * 注意：在1%精度下，0~100进行斐波那契散列运算不会产生哈希冲突，若精度需要更高呢？
     *
     * @param strategyId 策略ID
     * @param awardRateInfoList 奖品概率配置集合，保存奖品id及其中奖概率
     */
    void initRateTuple(Long strategyId, List<AwardRateVO> awardRateInfoList);

    /**
     * 判断是否已经做了数据初始化
     * @param strategyId 策略ID
     */
    boolean isExistRateTuple(Long strategyId);

    /**
     * 初始化奖品概率配置集合
     * @param strategyId 策略ID
     * @param awardRateInfoList 奖品概率配置集合，保存奖品id及其中奖概率
     */
    void initAwardRateInfoMap(Long strategyId, List<AwardRateVO> awardRateInfoList);

    /**
     * 随机抽奖
     *
     * @param strategyId 策略ID
     * @param excludeAwardIds 排除掉已经不能作为抽奖的奖品ID，留给风控和空库存使用
     * @return 中奖结果：奖品id
     */
    Long randomDraw(Long strategyId, List<Long> excludeAwardIds);
}
