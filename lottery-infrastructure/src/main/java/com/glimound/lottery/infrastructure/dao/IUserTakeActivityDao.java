package com.glimound.lottery.infrastructure.dao;

import com.glimound.db.router.annotation.DBRouter;
import com.glimound.lottery.infrastructure.po.UserTakeActivity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户领取活动DAO
 * @author Glimound
 */
@Mapper
public interface IUserTakeActivityDao {

    /**
     * 插入用户领取活动信息
     *
     * @param userTakeActivity 入参
     */
    void insertUserTakeActivity(UserTakeActivity userTakeActivity);

    /**
     * 锁定活动领取记录
     *
     * @param userTakeActivity  入参
     * @return                  更新结果
     */
    int lockTakeActivity(UserTakeActivity userTakeActivity);

    /**
     * 查询是否存在未执行抽奖领取活动单
     * 查询此活动ID，用户最早领取但未消费的一条记录
     *
     * @param userTakeActivity 请求入参
     * @return                 领取结果
     */
    @DBRouter(key = "uId")
    UserTakeActivity getNoConsumedTakeActivityOrder(UserTakeActivity userTakeActivity);

}
