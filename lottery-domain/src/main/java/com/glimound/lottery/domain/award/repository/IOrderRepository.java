package com.glimound.lottery.domain.award.repository;

/**
 * @author Gimound
 */
public interface IOrderRepository {

    /**
     * 更新奖品发放状态
     *
     * @param uId               用户ID
     * @param orderId           订单ID
     * @param awardId           奖品ID
     * @param grantState        奖品状态
     */
    void updateAwardGrantState(String uId, Long orderId, Long awardId, Integer grantState);

}
