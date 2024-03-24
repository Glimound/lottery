package com.glimound.lottery.domain.strategy.service.algorithm.impl;

import com.glimound.lottery.domain.strategy.model.vo.AwardRateVO;
import com.glimound.lottery.domain.strategy.service.algorithm.BaseAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单项概率抽奖：使用Alias Method别名抽样算法，SecureRandom 生成随机数，索引到对应的奖品信息返回结果；概率不会随奖品抽空而改变
 * 初始化复杂度O(n)，采样复杂度O(1)
 * @author Glimound
 */
@Component("singleRateRandomDrawAlgorithm")
@Slf4j
public class SingleRateRandomDrawAlgorithm extends BaseAlgorithm {

    /**
     * 别名采样方法的alias table
     */
    static class AliasTable {
        /** 记录奖品id对应的位置 */
        Long[] awardIds;
        /** 记录各位置对应的事件的概率 */
        BigDecimal[] rates;
        /** 记录补位事件对应的位置，若该位置无补位事件，则为null */
        Integer[] indexes;

        AliasTable(Long[] awardIds, BigDecimal[] rates, Integer[] indexs) {
            this.awardIds = awardIds;
            this.rates = rates;
            this.indexes = indexs;
        }
    }

    /** rateTuple: 策略id与alias table的映射；strategyId -> aliasTable */
    private final Map<Long, AliasTable> aliasTableMap = new ConcurrentHashMap<>();

    @Override
    public void initializeIfAbsent(Long strategyId, List<AwardRateVO> awardRateInfoList) {

        // 若已初始化过，返回
        if (aliasTableMap.containsKey(strategyId)) {
            return;
        }

        int length = awardRateInfoList.size();
        AliasTable aliasTable = new AliasTable(new Long[length], new BigDecimal[length], new Integer[length]);
        ArrayDeque<Integer> smallQueue = new ArrayDeque<>();
        ArrayDeque<Integer> bigQueue = new ArrayDeque<>();

        // 初始化奖品id数组和概率数组，令概率 * length并判断是否大于1，大于1则进入大数队列（待拆分），小于1则进入小数队列（待补齐）
        for (int i = 0; i < length; i++) {
            BigDecimal rate = awardRateInfoList.get(i).getAwardRate().multiply(BigDecimal.valueOf(length));
            aliasTable.awardIds[i] = awardRateInfoList.get(i).getAwardId();
            aliasTable.rates[i] = rate;
            if (rate.compareTo(BigDecimal.ONE) > 0) {
                bigQueue.add(i);
            } else if (rate.compareTo(BigDecimal.ONE) < 0) {
                smallQueue.add(i);
            }
        }

        // 遍历直到两队列为空
        while (!(smallQueue.isEmpty() && bigQueue.isEmpty())) {
            int bigRateIndex = bigQueue.remove();
            int smallRateIndex = smallQueue.remove();
            // bigRate -= 1 - smallRate
            aliasTable.rates[bigRateIndex] = aliasTable.rates[bigRateIndex].subtract(
                    BigDecimal.ONE.subtract(aliasTable.rates[smallRateIndex]));
            aliasTable.indexes[smallRateIndex] = bigRateIndex;
            if (aliasTable.rates[bigRateIndex].compareTo(BigDecimal.ONE) > 0) {
                bigQueue.add(bigRateIndex);
            } else if (aliasTable.rates[bigRateIndex].compareTo(BigDecimal.ONE) < 0) {
                smallQueue.add(bigRateIndex);
            }
        }

        aliasTableMap.put(strategyId, aliasTable);
    }

    @Override
    public Long randomDraw(Long strategyId, List<Long> excludeAwardIds) {

        AliasTable aliasTable = aliasTableMap.get(strategyId);
        assert aliasTable != null;

        // 生成0~length的随机数
        int randomInt = this.generateSecureRandomIntCode(aliasTable.awardIds.length);
        // 生成double概率随机数
        double randomDouble = this.generateSecureRandomDoubleCode();

        Long awardId;
        if (BigDecimal.valueOf(randomDouble).compareTo(aliasTable.rates[randomInt]) >= 0) {
            awardId = aliasTable.awardIds[aliasTable.indexes[randomInt]];
        } else {
            awardId = aliasTable.awardIds[randomInt];
        }

        if (excludeAwardIds.contains(awardId)) {
            return null;
        }
        return awardId;
    }
}
