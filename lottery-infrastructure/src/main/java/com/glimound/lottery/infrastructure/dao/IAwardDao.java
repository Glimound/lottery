package com.glimound.lottery.infrastructure.dao;

import com.glimound.lottery.infrastructure.po.Award;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Glimound
 */
@Mapper
public interface IAwardDao {

    /**
     * 查询奖品信息
     *
     * @param awardId 奖品ID
     * @return        奖品信息
     */
    Award getAwardById(Long awardId);
}
