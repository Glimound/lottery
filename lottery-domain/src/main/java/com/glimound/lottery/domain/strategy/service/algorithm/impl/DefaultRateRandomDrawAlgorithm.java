package com.glimound.lottery.domain.strategy.service.algorithm.impl;

import com.glimound.lottery.domain.strategy.model.vo.AwardRateInfo;
import com.glimound.lottery.domain.strategy.service.algorithm.BaseAlgorithm;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * 总体概率抽奖：排除已抽空的奖项，剩余奖项按照比例重新计算概率
 * @author Glimound
 */
@Component("defaultRateRandomDrawAlgorithm")
public class DefaultRateRandomDrawAlgorithm extends BaseAlgorithm {
    @Override
    public Long randomDraw(Long strategyId, List<Long> excludeAwardIds) {

        List<AwardRateInfo> availableAwards = new ArrayList<>();
        BigDecimal rateDenominator = BigDecimal.ZERO;

        // 排除不可被抽中的奖项，并重新计算总概率的分母
        for (AwardRateInfo awardRateInfo : awardRateInfoMap.get(strategyId)) {
            if (!excludeAwardIds.contains(awardRateInfo.getAwardId())) {
                availableAwards.add(awardRateInfo);
                rateDenominator = rateDenominator.add(awardRateInfo.getAwardRate());
            }
        }

        if (availableAwards.size() == 0) {
            return -1L;
        }
        if (availableAwards.size() == 1) {
            return availableAwards.get(0).getAwardId();
        }

        // 获取1~100的随机数
        int randomVal = new SecureRandom().nextInt(100) + 1;

        int rateSum = 0;
        for (AwardRateInfo awardRateInfo : availableAwards) {
            int rateVal = awardRateInfo.getAwardRate().divide(rateDenominator, 2, RoundingMode.UP)
                    .multiply(new BigDecimal(100)).intValue();
            rateSum += rateVal;
            if (randomVal <= rateSum) {
                return awardRateInfo.getAwardId();
            }
        }

        return -1L;
    }
}
