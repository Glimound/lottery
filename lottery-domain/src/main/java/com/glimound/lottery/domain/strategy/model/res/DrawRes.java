package com.glimound.lottery.domain.strategy.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Glimound
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DrawRes {
    // 用户ID
    private String uId;

    // 策略ID
    private Long strategyId;

    // 奖品ID
    private Long awardId;

    // 奖品名称
    private String awardName;
}
