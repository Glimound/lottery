package com.glimound.lottery.infrastructure.dao;

import com.glimound.lottery.infrastructure.po.StrategyDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Glimound
 */
@Mapper
public interface IStrategyDetailDao {
    List<StrategyDetail> listStrategyDetailById(Long strategyId);
}
