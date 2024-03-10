package com.glimound.lottery.infrastructure.dao;

import com.glimound.lottery.infrastructure.po.StrategyDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Glimound
 */
@Mapper
public interface IStrategyDetailDao {
    /**
     * 查询策略表详细配置
     * @param strategyId 策略ID
     * @return           返回结果
     */
    List<StrategyDetail> listStrategyDetailById(Long strategyId);

    /**
     * 查询无库存策略奖品ID
     * @param strategyId 策略ID
     * @return           返回结果
     */
    List<Long> listNoStockStrategyAwardById(Long strategyId);

    /**
     * 扣减库存
     * @param strategyDetail 策略ID、奖品ID
     * @return                  返回结果
     */
    int deductStockById(StrategyDetail strategyDetail);
}
