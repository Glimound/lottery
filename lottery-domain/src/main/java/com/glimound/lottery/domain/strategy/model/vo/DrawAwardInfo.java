package com.glimound.lottery.domain.strategy.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 中奖奖品信息
 * @author Glimound
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DrawAwardInfo {

    /**
     * 奖品ID
     */
    private Long rewardId;

    /**
     * 奖品名称
     */
    private String awardName;
}
