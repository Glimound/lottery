package com.glimound.lottery.domain.strategy.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 奖品概率信息，奖品编号、概率
 * @author Glimound
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AwardRateInfo {
    // 奖品ID
    private Long awardId;

    // 中奖概率
    private BigDecimal awardRate;
}
