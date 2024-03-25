package com.glimound.lottery.infrastructure.dao;

import com.glimound.db.router.annotation.DBRouter;
import com.glimound.db.router.annotation.DBRouterStrategy;
import com.glimound.lottery.infrastructure.po.UserStrategyExport;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户策略计算结果表DAO
 * @author Glimound
 */
@DBRouterStrategy(splitTable = true)
@Mapper
public interface IUserStrategyExportDao {

    /**
     * 新增数据
     * @param userStrategyExport 用户策略
     */
    @DBRouter(key = "uId")
    void insertUserStrategyExport(UserStrategyExport userStrategyExport);

    /**
     * 查询数据
     * @param uId 用户ID
     * @return 用户策略
     */
    @DBRouter(key = "uId")
    UserStrategyExport getUserStrategyExportByUId(String uId);

    /**
     * 更新发奖状态
     * @param userStrategyExport 发奖信息
     */
    @DBRouter(key = "uId")
    void updateAwardGrantState(UserStrategyExport userStrategyExport);

    /**
     * 更新发送MQ状态
     * @param userStrategyExport 发送消息
     */
    @DBRouter(key = "uId")
    void updateMqState(UserStrategyExport userStrategyExport);

}
