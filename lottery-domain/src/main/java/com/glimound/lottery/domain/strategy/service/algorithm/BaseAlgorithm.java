package com.glimound.lottery.domain.strategy.service.algorithm;

import com.glimound.lottery.domain.strategy.model.vo.AwardRateInfo;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 共用的算法逻辑
 * @author Glimound
 */
@Slf4j
public abstract class BaseAlgorithm implements IDrawAlgorithm {

    // 斐波那契散列增量（魔数），逻辑：黄金分割点：(√5 - 1) / 2 = 0.6180339887，Math.pow(2, 32) * 0.6180339887 = 0x61c88647
    public final int HASH_INCREMENT = 0x61c88647;

    // 数组初始化长度，用于按位与运算
    private final int RATE_TUPLE_LENGTH = 128;

    // rateTuple: 概率与奖品对应的散列结果；strategyId -> rateTuple
    protected Map<Long, Long[]> rateTupleMap = new ConcurrentHashMap<>();

    // 奖品区间概率值，strategyId -> [awardId & rate, awardId & rate]
    protected Map<Long, List<AwardRateInfo>> awardRateInfoMap = new ConcurrentHashMap<>();

    @Override
    public void initRateTuple(Long strategyId, List<AwardRateInfo> awardRateInfoList) {
        initAwardRateInfoMap(strategyId, awardRateInfoList);

        // 若已有该key，返回value（对应的rateTuple）
        // 若无该key，执行传入的函数（生成一个新的rateTuple），将其结果作为value，并返回该value
        Long[] rateTuple = rateTupleMap.computeIfAbsent(strategyId, k -> new Long[RATE_TUPLE_LENGTH]);

        int pos = 1;
        for (AwardRateInfo awardRateInfo : awardRateInfoList) {
            int rateVal = awardRateInfo.getAwardRate().multiply(new BigDecimal(100)).intValue();
            // 将概率值对应范围内的数进行斐波那契散列运算
            for (int i = 0; i < rateVal; i++, pos++) {
                rateTuple[hashIdx(pos)] = awardRateInfo.getAwardId();
            }
        }
    }

    @Override
    public void initAwardRateInfoMap(Long strategyId, List<AwardRateInfo> awardRateInfoList) {
        awardRateInfoMap.put(strategyId, awardRateInfoList);
    }

    @Override
    public boolean isExistRateTuple(Long strategyId) {
        return rateTupleMap.containsKey(strategyId);
    }

    /**
     * 斐波那契散列法，计算哈希索引下标值
     *
     * @param val 值
     * @return 索引
     */
    protected int hashIdx(int val) {
        int hashCode = val * HASH_INCREMENT + HASH_INCREMENT;
        return hashCode & (RATE_TUPLE_LENGTH - 1);
    }

}
