package com.glimound.lottery.domain.strategy.service.algorithm;

import com.glimound.lottery.domain.strategy.model.vo.AwardRateVO;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 共用的算法逻辑
 * @author Glimound
 */
@Slf4j
public abstract class BaseAlgorithm implements IDrawAlgorithm {

    // 奖品区间概率值，strategyId -> [awardId & rate, awardId & rate]
    protected Map<Long, List<AwardRateVO>> awardRateInfoMap = new ConcurrentHashMap<>();

    @Override
    public void initAwardRateInfoMap(Long strategyId, List<AwardRateVO> awardRateInfoList) {
        awardRateInfoMap.put(strategyId, awardRateInfoList);
    }

    /**
     * 生成随机int抽奖码
     * @return 随机值
     */
    protected int generateSecureRandomIntCode(int bound) {
        return new SecureRandom().nextInt(bound);
    }

    /**
     * 生成随机double抽奖码
     * @return 随机值
     */
    protected double generateSecureRandomDoubleCode() {
        return new SecureRandom().nextDouble();
    }

}
