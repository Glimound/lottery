package com.glimound.lottery.infrastructure.dao;

import com.glimound.lottery.domain.activity.model.vo.AlterStateVO;
import com.glimound.lottery.infrastructure.po.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
    void insertActivity(Activity activity);

    /**
     * 根据活动号查询活动信息
     *
     * @param activityId 活动号
     * @return           活动信息
     */
    Activity getActivityById(Long activityId);

    /**
     * 变更活动状态
     *
     * @param alterStateVO  [activityId、beforeState、afterState]
     * @return 更新数量
     */
    int alterState(AlterStateVO alterStateVO);

    /**
     * 扣减活动库存
     * @param activityId 活动ID
     * @return 更新数量
     */
    int deductActivityStockById(Long activityId);

    /**
     * 扫描待处理的活动列表，状态为：通过、活动中
     *
     * @param id ID
     * @return 待处理的活动集合
     */
    List<Activity> listToDoActivity(Long id);

}
