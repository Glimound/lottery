package com.glimound.lottery.domain.activity.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 活动参与记录
 * @author Glimound
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActivityPartakeRecordVO {
    /** 用户ID */
    private String uId;
    /** activity 活动ID */
    private Long activityId;
    /** 库存 */
    private Integer stockCount;
    /** 库存剩余 */
    private Integer stockSurplusCount;
}
