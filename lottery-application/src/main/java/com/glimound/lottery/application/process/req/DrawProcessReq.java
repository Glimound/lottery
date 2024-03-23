package com.glimound.lottery.application.process.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 抽奖请求
 * @author Glimound
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DrawProcessReq {

    /** 用户ID */
    private String uId;
    /** 活动ID */
    private Long activityId;
    /** 参与时间 */
    private Date partakeDate;

}
