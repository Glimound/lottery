package com.glimound.lottery.domain.strategy.service.algorithm.impl;

import com.glimound.lottery.domain.strategy.model.vo.AwardRateVO;
import com.glimound.lottery.domain.strategy.service.algorithm.BaseAlgorithm;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 总体概率抽奖：排除已抽空的奖项，剩余奖项按照比例重新计算概率
 * 无需初始化，采样复杂度O(n)
 * @author Glimound
 */
@Component("entiretyRateRandomDrawAlgorithm")
public class EntiretyRateRandomDrawAlgorithm extends BaseAlgorithm {

    /** 无需初始化 */
    @Override
    public void initializeIfAbsent(Long strategyId, List<AwardRateVO> awardRateInfoList) { }

    @Override
    public Long randomDraw(Long strategyId, List<Long> excludeAwardIds) {

        List<AwardRateVO> availableAwards = new ArrayList<>();
        BigDecimal rateNew = BigDecimal.ZERO;

        // 排除不可被抽中的奖项，并重新计算总概率
        for (AwardRateVO awardRateVO : awardRateInfoMap.get(strategyId)) {
            if (!excludeAwardIds.contains(awardRateVO.getAwardId())) {
                availableAwards.add(awardRateVO);
                rateNew = rateNew.add(awardRateVO.getAwardRate());
            }
        }

        if (availableAwards.size() == 0) {
            return null;
        }
        if (availableAwards.size() == 1) {
            return availableAwards.get(0).getAwardId();
        }

        // 获取0~rateNew的随机数
        BigDecimal randomVal = BigDecimal.valueOf(this.generateSecureRandomDoubleCode()).multiply(rateNew);

        BigDecimal rateSum = BigDecimal.ZERO;
        for (int i = 0; i < availableAwards.size(); i++) {
            rateSum = rateSum.add(availableAwards.get(i).getAwardRate());
            if (randomVal.compareTo(rateSum) < 0) {
                return availableAwards.get(i).getAwardId();
            }
        }

        return null;
    }
}
