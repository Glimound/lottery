package com.glimound.lottery.domain.award.service.factory;

import com.glimound.lottery.common.Constants;
import com.glimound.lottery.domain.award.service.goods.IDistributionGoods;
import com.glimound.lottery.domain.award.service.goods.impl.CouponGoods;
import com.glimound.lottery.domain.award.service.goods.impl.DescGoods;
import com.glimound.lottery.domain.award.service.goods.impl.PhysicalGoods;
import com.glimound.lottery.domain.award.service.goods.impl.RedeemCodeGoods;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 奖品工厂配置类，存放各类奖品的实例
 * @author Glimound
 */
public class GoodsConfig {

    /** 奖品发放策略组 */
    protected static Map<Integer, IDistributionGoods> goodsMap = new ConcurrentHashMap<>();

    @Resource
    private DescGoods descGoods;

    @Resource
    private RedeemCodeGoods redeemCodeGoods;

    @Resource
    private CouponGoods couponGoods;

    @Resource
    private PhysicalGoods physicalGoods;

    @PostConstruct
    public void init() {
        goodsMap.put(Constants.AwardType.DESC.getCode(), descGoods);
        goodsMap.put(Constants.AwardType.RedeemCodeGoods.getCode(), redeemCodeGoods);
        goodsMap.put(Constants.AwardType.CouponGoods.getCode(), couponGoods);
        goodsMap.put(Constants.AwardType.PhysicalGoods.getCode(), physicalGoods);
    }
}
