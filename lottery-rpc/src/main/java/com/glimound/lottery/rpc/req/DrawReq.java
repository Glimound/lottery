package com.glimound.lottery.rpc.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 抽奖请求
 * @author Glimound
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DrawReq implements Serializable {
    /** 用户ID */
    private String uId;
    /** 活动ID */
    private Long activityId;
}
