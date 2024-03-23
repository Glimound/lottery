package com.glimound.lottery.domain.strategy.service.algorithm.impl;

import com.glimound.lottery.domain.strategy.model.vo.AwardRateVO;
import com.glimound.lottery.domain.strategy.service.algorithm.BaseAlgorithm;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 总体概率抽奖：排除已抽空的奖项，剩余奖项按照比例重新计算概率
 * @author Glimound
 */
@Component("entiretyRateRandomDrawAlgorithm")
public class EntiretyRateRandomDrawAlgorithm extends BaseAlgorithm {
    @Override
    public Long randomDraw(Long strategyId, List<Long> excludeAwardIds) {

        List<AwardRateVO> availableAwards = new ArrayList<>();
        BigDecimal rateDenominator = BigDecimal.ZERO;

        // 排除不可被抽中的奖项，并重新计算总概率的分母
        for (AwardRateVO awardRateVO : awardRateInfoMap.get(strategyId)) {
            if (!excludeAwardIds.contains(awardRateVO.getAwardId())) {
                availableAwards.add(awardRateVO);
                rateDenominator = rateDenominator.add(awardRateVO.getAwardRate());
            }
        }

        if (availableAwards.size() == 0) {
            return null;
        }
        if (availableAwards.size() == 1) {
            return availableAwards.get(0).getAwardId();
        }

        // 获取1~100的随机数
        int randomVal = this.generateSecureRandomIntCode(100);

        int rateSum = 0;
        for (AwardRateVO awardRateVO : availableAwards) {
            int rateVal = awardRateVO.getAwardRate().divide(rateDenominator, 2, RoundingMode.UP)
                    .multiply(new BigDecimal(100)).intValue();
            rateSum += rateVal;
            if (randomVal <= rateSum) {
                return awardRateVO.getAwardId();
            }
        }

        return null;
    }
}
