package com.glimound.lottery.domain.award.service.goods.impl;

import com.glimound.lottery.common.Constants;
import com.glimound.lottery.domain.award.model.req.GoodsReq;
import com.glimound.lottery.domain.award.model.res.DistributionRes;
import com.glimound.lottery.domain.award.service.goods.DistributionBase;
import com.glimound.lottery.domain.award.service.goods.IDistributionGoods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 货物：实物
 * @author Glimound
 */
@Slf4j
@Component
public class PhysicalGoods extends DistributionBase implements IDistributionGoods {
    @Override
    public DistributionRes doDistribution(GoodsReq req) {
        // 模拟调用实物发放接口
        log.info("模拟调用实物发放接口 uId：{} awardContent：{}", req.getUId(), req.getAwardContent());

        // 更新用户领奖结果
        super.updateUserAwardState(req.getUId(), req.getOrderId(), req.getAwardId(), Constants.GrantState.COMPLETE.getCode());

        return new DistributionRes(req.getUId(), Constants.AwardState.SUCCESS.getCode(),
                Constants.AwardState.SUCCESS.getInfo());
    }
}