package com.glimound.lottery.domain.activity.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 参与活动请求
 * @author Glimound
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PartakeReq {

    /** 用户ID */
    private String uId;
    /** 活动ID */
    private Long activityId;
    /** 活动领取时间 */
    private Date partakeDate;

}
