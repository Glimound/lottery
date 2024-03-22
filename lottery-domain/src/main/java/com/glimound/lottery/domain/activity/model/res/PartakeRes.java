package com.glimound.lottery.domain.activity.model.res;

import com.glimound.lottery.common.Result;

/**
 * 活动参与结果
 * @author Glimound
 */
public class PartakeRes extends Result {

    /**
     * 策略ID
     */
    private Long strategyId;

    public PartakeRes(String code, String info) {
        super(code, info);
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }
}
