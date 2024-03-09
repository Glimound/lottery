package com.glimound.lottery.rpc.req;

import lombok.*;

import java.io.Serializable;

/**
 * @author Glimoud
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActivityReq implements Serializable {
    private Long activityId;
}
