package com.glimound.lottery.infrastructure.dao;

import com.glimound.lottery.infrastructure.po.Award;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Glimound
 */
@Mapper
public interface IAwardDao {

    Award getAwardById(Long awardId);
}
