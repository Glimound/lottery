package com.glimound.lottery.domain.activity.model.res;

import com.glimound.lottery.common.Result;


/**
 * 库存处理结果
 * @author Glimound
 */
public class StockRes extends Result {

    /**
     * activity 库存剩余
     */
    private Integer stockSurplusCount;

    public StockRes(String code, String info) {
        super(code, info);
    }

    public StockRes(String code, String info, Integer stockSurplusCount) {
        super(code, info);
        this.stockSurplusCount = stockSurplusCount;
    }

    public Integer getStockSurplusCount() {
        return stockSurplusCount;
    }

    public void setStockSurplusCount(Integer stockSurplusCount) {
        this.stockSurplusCount = stockSurplusCount;
    }
}