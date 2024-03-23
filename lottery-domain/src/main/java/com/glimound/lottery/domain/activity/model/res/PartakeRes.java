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

    /**
     * 活动领取ID
     */
    private Long takeId;

    public PartakeRes(String code, String info) {
        super(code, info);
    }

    public PartakeRes(String code, String info, Long strategyId, Long takeId) {
        super(code, info);
        this.strategyId = strategyId;
        this.takeId = takeId;
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
}
