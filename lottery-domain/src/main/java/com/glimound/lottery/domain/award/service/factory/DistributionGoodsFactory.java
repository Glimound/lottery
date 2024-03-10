package com.glimound.lottery.domain.award.service.factory;

import com.glimound.lottery.domain.award.service.goods.IDistributionGoods;
import org.springframework.stereotype.Service;

/**
 * 配送商品简单工厂，提供获取配送服务
 * @author Glimound
 */
@Service
public class DistributionGoodsFactory extends GoodsConfig {
    public IDistributionGoods getDistributionGoodsService(Integer awardType){
        return goodsMap.get(awardType);
    }
}
