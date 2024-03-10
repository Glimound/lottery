package com.glimound.lottery.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 策略明细
 * @author Glimound
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StrategyDetail {
    // 自增ID
    private Long id;

    // 策略ID
    private Long strategyId;

    // 奖品ID
    private Long awardId;

    // 奖品数量
    private Integer awardCount;

    // 中奖概率
    private BigDecimal awardRate;

    // 创建时间
    private Date createTime;

    // 修改时间
    private Date updateTime;
}
