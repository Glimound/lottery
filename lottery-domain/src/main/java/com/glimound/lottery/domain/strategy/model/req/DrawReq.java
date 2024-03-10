package com.glimound.lottery.domain.strategy.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Glimound
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DrawReq {
    // 用户ID
    private String uId;

    // 策略ID
    private Long strategyId;
}
