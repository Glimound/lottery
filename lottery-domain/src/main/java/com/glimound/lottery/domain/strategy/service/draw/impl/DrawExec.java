package com.glimound.lottery.domain.strategy.service.draw.impl;

import com.glimound.lottery.domain.strategy.model.aggregate.StrategyRich;
import com.glimound.lottery.domain.strategy.model.req.DrawReq;
import com.glimound.lottery.domain.strategy.model.res.DrawRes;
import com.glimound.lottery.domain.strategy.repository.IStrategyRepository;
import com.glimound.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import com.glimound.lottery.domain.strategy.service.draw.DrawBase;
import com.glimound.lottery.domain.strategy.service.draw.IDrawExec;
import com.glimound.lottery.infrastructure.po.Award;
import com.glimound.lottery.infrastructure.po.Strategy;
import com.glimound.lottery.infrastructure.po.StrategyDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 执行抽奖的实现类
 * @author Glimound
 */
@Service("drawExec")
public class DrawExec extends DrawBase implements IDrawExec {

    private Logger logger = LoggerFactory.getLogger(DrawExec.class);

    @Resource
    private IStrategyRepository strategyRepository;

    @Override
    public DrawRes doDrawExec(DrawReq req) {
        logger.info("执行策略抽奖开始，strategyId：{}", req.getStrategyId());

        // 获取抽奖策略配置数据
        StrategyRich strategyRich = strategyRepository.getStrategyRichById(req.getStrategyId());
        Strategy strategy = strategyRich.getStrategy();
        List<StrategyDetail> strategyDetailList = strategyRich.getStrategyDetailList();

        // 校验和初始化数据
        checkAndInitRateData(strategy.getStrategyId(), strategy.getStrategyMode(), strategyDetailList);

        // 根据策略方式抽奖
        IDrawAlgorithm drawAlgorithm = drawAlgorithmMap.get(strategy.getStrategyMode());
        Long awardId = drawAlgorithm.randomDraw(strategy.getStrategyId(), new ArrayList<>());

        // 获取奖品信息
        Award award = strategyRepository.getAwardById(awardId);

        logger.info("执行策略抽奖完成，中奖用户：{} 奖品ID：{} 奖品名称：{}", req.getUId(), awardId, award.getAwardName());

        return new DrawRes(req.getUId(), strategyRich.getStrategyId(), awardId, award.getAwardName());
    }
}
