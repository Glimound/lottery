package com.glimound.lottery.domain.strategy.repository.impl;

import com.glimound.lottery.domain.strategy.model.aggregate.StrategyRich;
import com.glimound.lottery.domain.strategy.repository.IStrategyRepository;
import com.glimound.lottery.infrastructure.dao.IAwardDao;
import com.glimound.lottery.infrastructure.dao.IStrategyDao;
import com.glimound.lottery.infrastructure.dao.IStrategyDetailDao;
import com.glimound.lottery.infrastructure.po.Award;
import com.glimound.lottery.infrastructure.po.Strategy;
import com.glimound.lottery.infrastructure.po.StrategyDetail;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Glimound
 */
@Component
public class StrategyRepository implements IStrategyRepository {

    @Resource
    private IStrategyDao strategyDao;

    @Resource
    private IStrategyDetailDao strategyDetailDao;

    @Resource
    private IAwardDao awardDao;

    @Override
    public StrategyRich getStrategyRichById(Long strategyId) {
        // 此处使用多次查询组装，而非join
        Strategy strategy = strategyDao.getStrategyById(strategyId);
        List<StrategyDetail> strategyDetailList = strategyDetailDao.listStrategyDetailById(strategyId);
        return new StrategyRich(strategyId, strategy, strategyDetailList);
    }

    @Override
    public Award getAwardById(Long awardId) {
        return awardDao.getAwardById(awardId);
    }
}
