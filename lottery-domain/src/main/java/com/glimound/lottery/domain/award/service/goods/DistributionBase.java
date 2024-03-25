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

    protected void updateUserAwardState(String uId, Long orderId, Long awardId, Integer grantState) {
        orderRepository.updateAwardGrantState(uId, orderId, awardId, grantState);
    }

}
