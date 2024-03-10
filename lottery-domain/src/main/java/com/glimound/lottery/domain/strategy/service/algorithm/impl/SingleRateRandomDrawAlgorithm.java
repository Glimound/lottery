package com.glimound.lottery.domain.strategy.service.algorithm.impl;

import com.glimound.lottery.domain.strategy.service.algorithm.BaseAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;

/**
 * 单项概率抽奖：SecureRandom 生成随机数，索引到对应的奖品信息返回结果；概率不会随奖品抽空而改变
 * @author Glimound
 */
@Component("singleRateRandomDrawAlgorithm")
@Slf4j
public class SingleRateRandomDrawAlgorithm extends BaseAlgorithm {
    @Override
    public Long randomDraw(Long strategyId, List<Long> excludeAwardIds) {

        Long[] rateTuple = rateTupleMap.get(strategyId);
        assert rateTuple != null;

        // 生成1~100的随机数
        int randomVal = new SecureRandom().nextInt(100) + 1;
        Long awardId = rateTuple[hashIdx(randomVal)];

        if (excludeAwardIds.contains(awardId)) {
            return -1L;
        }

        return awardId;
    }
}
