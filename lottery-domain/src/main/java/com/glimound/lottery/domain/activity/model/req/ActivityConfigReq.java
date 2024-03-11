package com.glimound.lottery.domain.activity.model.req;

import com.glimound.lottery.domain.activity.model.aggregates.ActivityConfigRich;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 活动配置请求对象
 * @author Glimound
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActivityConfigReq {
    /** 活动ID */
    private Long activityId;

    /** 活动配置信息 */
    private ActivityConfigRich activityConfigRich;
}
