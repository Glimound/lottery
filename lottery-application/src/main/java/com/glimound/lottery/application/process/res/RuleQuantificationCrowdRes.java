package com.glimound.lottery.application.process.res;

import com.glimound.lottery.common.Result;

/**
 * @author Glimound
 */
public class RuleQuantificationCrowdRes extends Result {

    /** 活动ID */
    private Long activityId;

    public RuleQuantificationCrowdRes(String code, String info) {
        super(code, info);
    }
    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }
}
