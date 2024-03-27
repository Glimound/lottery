package com.glimound.lottery.domain.activity.model.res;

import com.glimound.lottery.common.Result;

/**
 * 活动参与结果
 * @author Glimound
 */
public class PartakeRes extends Result {

    /** 策略ID */
    private Long strategyId;
    /** 活动领取ID */
    private Long takeId;
    /** 剩余库存 */
    private Integer stockSurplusCount;

    public PartakeRes(String code, String info) {
        super(code, info);
    }

    public PartakeRes(String code, String info, Long strategyId, Long takeId) {
        super(code, info);
        this.strategyId = strategyId;
        this.takeId = takeId;
    }

    public PartakeRes(String code, String info, Long strategyId, Long takeId, Integer stockSurplusCount) {
        super(code, info);
        this.strategyId = strategyId;
        this.takeId = takeId;
        this.stockSurplusCount = stockSurplusCount;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public Long getTakeId() {
        return takeId;
    }

    public void setTakeId(Long takeId) {
        this.takeId = takeId;
    }

    public Integer getStockSurplusCount() {
        return stockSurplusCount;
    }

    public void setStockSurplusCount(Integer stockSurplusCount) {
        this.stockSurplusCount = stockSurplusCount;
    }
}
