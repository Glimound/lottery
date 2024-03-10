package com.glimound.lottery.infrastructure.dao;

import com.glimound.lottery.infrastructure.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Glimound
 */
@Mapper
public interface IStrategyDao {
    Strategy getStrategyById(Long strategyId);
}
