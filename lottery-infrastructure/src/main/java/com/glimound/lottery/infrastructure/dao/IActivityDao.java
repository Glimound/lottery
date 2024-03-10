package com.glimound.lottery.infrastructure.dao;

import com.glimound.lottery.infrastructure.po.Activity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据访问层
 * @author Glimound
 */
@Mapper
public interface IActivityDao {

    /**
     * 插入数据
     * @param activity 入参
     */
    void insert(Activity activity);

    /**
     * 根据活动号查询活动信息
     *
     * @param activityId 活动号
     * @return           活动信息
     */
    Activity getActivityById(Long activityId);
}
