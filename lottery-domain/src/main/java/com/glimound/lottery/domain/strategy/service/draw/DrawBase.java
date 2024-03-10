package com.glimound.lottery.domain.strategy.service.draw;

import com.glimound.lottery.domain.strategy.model.vo.AwardRateInfo;
import com.glimound.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import com.glimound.lottery.infrastructure.po.StrategyDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Glimound
 */
public class DrawBase extends DrawConfig {

    /**
     * 根据
     * @param strategyId
     * @param strategyMode
     * @param strategyDetailList
     */
    public void checkAndInitRateData(Long strategyId, Integer strategyMode, List<StrategyDetail> strategyDetailList) {
        IDrawAlgorithm drawAlgorithm = drawAlgorithmMap.get(strategyMode);

        // 若非单项概率算法，则无需初始化rateTuple，仅需初始化awardRateInfoMap即可
        if (strategyMode != 1) {
            List<AwardRateInfo> awardRateInfoList = new ArrayList<>(strategyDetailList.size());
            for (StrategyDetail strategyDetail : strategyDetailList) {
                awardRateInfoList.add(new AwardRateInfo(strategyDetail.getAwardId(), strategyDetail.getAwardRate()));
            }
            drawAlgorithm.initAwardRateInfoMap(strategyId, awardRateInfoList);
            return;
        }

        // 若rateTuple已初始化过，则return
        if (drawAlgorithm.isExistRateTuple(strategyId)) {
            return;
        }

        List<AwardRateInfo> awardRateInfoList = new ArrayList<>(strategyDetailList.size());
        for (StrategyDetail strategyDetail : strategyDetailList) {
            awardRateInfoList.add(new AwardRateInfo(strategyDetail.getAwardId(), strategyDetail.getAwardRate()));
        }

        drawAlgorithm.initRateTuple(strategyId, awardRateInfoList);
    }

}
