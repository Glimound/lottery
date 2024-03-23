package com.glimound.lottery.domain.award.service.goods;

import com.glimound.lottery.domain.award.repository.IOrderRepository;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * 配送货物基础共用类
 * @author Glimound
 */
@Slf4j
public class DistributionBase {

    @Resource
    private IOrderRepository orderRepository;

    protected void updateUserAwardState(String uId, String orderId, Long awardId, Integer awardState, String awardStateInfo) {
        log.info("TODO 后期添加更新分库分表中，用户个人的抽奖记录表中奖品发奖状态 uId：{}", uId);
    }

}
