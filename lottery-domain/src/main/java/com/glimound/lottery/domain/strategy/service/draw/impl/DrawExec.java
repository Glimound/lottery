package com.glimound.lottery.domain.strategy.service.draw.impl;

import com.alibaba.fastjson.JSON;
import com.glimound.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import com.glimound.lottery.domain.strategy.service.draw.AbstractDrawBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 执行抽奖的实现类
 * @author Glimound
 */
@Service("drawExec")
@Slf4j
public class DrawExec extends AbstractDrawBase {

    @Override
    protected List<Long> getExcludeAwardIds(Long strategyId) {
        List<Long> awardList = strategyRepository.listNoStockStrategyAwardById(strategyId);
        log.info("执行抽奖策略 strategyId：{}，无库存排除奖品列表ID集合 awardList：{}", strategyId, JSON.toJSONString(awardList));
        return awardList;
    }

    @Override
    protected Long drawAlgorithm(Long strategyId, IDrawAlgorithm drawAlgorithm, List<Long> excludeAwardIds) {
        // 执行抽奖
        Long awardId = drawAlgorithm.randomDraw(strategyId, excludeAwardIds);

        // 判断抽奖结果
        if (awardId == null) {
            return null;
        }

        /*
         * 扣减库存，暂时采用数据库行级锁的方式进行扣减库存，后续优化为 Redis 分布式锁扣减 decr/incr
         * 注意：通常数据库直接锁行记录的方式并不能支撑较大体量的并发，但此种方式需要了解，
         * 因为在分库分表下的正常数据流量下的个人数据记录中，是可以使用行级锁的，因为他只影响到自己的记录，不会影响到其他人
         */
        boolean isSuccess = strategyRepository.deductStockById(strategyId, awardId);

        // 返回结果，库存扣减成功返回奖品ID，否则返回null
        return isSuccess ? awardId : null;
    }
}
