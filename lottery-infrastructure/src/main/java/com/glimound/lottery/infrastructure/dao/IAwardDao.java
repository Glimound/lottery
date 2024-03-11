package com.glimound.lottery.infrastructure.dao;

import com.glimound.lottery.infrastructure.po.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * 插入奖品配置
     *
     * @param list 奖品配置
     */
    void insertAwardList(List<Award> list);
}
