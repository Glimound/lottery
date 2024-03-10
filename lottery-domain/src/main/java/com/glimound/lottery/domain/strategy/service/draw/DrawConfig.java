package com.glimound.lottery.domain.strategy.service.draw;

import com.glimound.lottery.common.Constants;
import com.glimound.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Glimound
 */
public class DrawConfig {
    @Resource
    private IDrawAlgorithm entiretyRateRandomDrawAlgorithm;

    @Resource
    private IDrawAlgorithm singleRateRandomDrawAlgorithm;

    // 抽奖策略组
    protected static Map<Integer, IDrawAlgorithm> drawAlgorithmGroup = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        drawAlgorithmGroup.put(Constants.StrategyMode.SINGLE.getCode(), singleRateRandomDrawAlgorithm);
        drawAlgorithmGroup.put(Constants.StrategyMode.ENTIRETY.getCode(), entiretyRateRandomDrawAlgorithm);
    }
}
