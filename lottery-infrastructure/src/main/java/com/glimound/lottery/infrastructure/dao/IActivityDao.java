package com.glimound.lottery.infrastructure.dao;

import com.glimound.lottery.infrastructure.po.Activity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据访问层
 * @author Glimound
 */
@Mapper
public interface IActivityDao {
    void insert(Activity activity);

    Activity getActivityById(Long activityId);
}
