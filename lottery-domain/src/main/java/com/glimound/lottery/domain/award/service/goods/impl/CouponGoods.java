package com.glimound.lottery.domain.award.service.goods.impl;

import com.glimound.lottery.common.Constants;
import com.glimound.lottery.domain.award.model.req.GoodsReq;
import com.glimound.lottery.domain.award.model.res.DistributionRes;
import com.glimound.lottery.domain.award.service.goods.DistributionBase;
import com.glimound.lottery.domain.award.service.goods.IDistributionGoods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 货物：优惠券
 * @author Glimound
 */
@Slf4j
@Component
public class CouponGoods extends DistributionBase implements IDistributionGoods {
    @Override
    public DistributionRes doDistribution(GoodsReq req) {
        // 模拟调用优惠券发放接口
        log.info("模拟调用优惠券发放接口 uId：{} awardContent：{}", req.getUId(), req.getAwardContent());

        // 更新用户领奖结果
        super.updateUserAwardState(req.getUId(), req.getOrderId(), req.getAwardId(),
                Constants.AwardState.SUCCESS.getCode(), Constants.AwardState.SUCCESS.getInfo());

        return new DistributionRes(req.getUId(), Constants.AwardState.SUCCESS.getCode(),
                Constants.AwardState.SUCCESS.getInfo());
    }

    @Override
    public Integer getDistributionGoodsName() {
        return Constants.AwardType.CouponGoods.getCode();
    }
}
