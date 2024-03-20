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
    @DBRouter(key = "uId")
    void insertUserTakeActivity(UserTakeActivity userTakeActivity);

}
