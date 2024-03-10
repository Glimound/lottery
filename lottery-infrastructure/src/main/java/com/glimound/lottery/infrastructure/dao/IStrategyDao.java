package com.glimound.lottery.infrastructure.dao;

import com.glimound.lottery.infrastructure.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Glimound
 */
@Mapper
public interface IStrategyDao {

    /**
     * 查询策略配置
     *
     * @param strategyId 策略ID
     * @return           策略配置信息
     */
    Strategy getStrategyById(Long strategyId);

}
