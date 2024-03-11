package com.glimound.lottery.domain.activity.model.aggregates;

import com.glimound.lottery.domain.activity.model.vo.ActivityVO;
import com.glimound.lottery.domain.activity.model.vo.AwardVO;
import com.glimound.lottery.domain.activity.model.vo.StrategyVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 活动配置聚合信息
 * @author Glimound
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActivityConfigRich {
    /** 活动配置 */
    private ActivityVO activity;

    /** 策略配置(含策略明细) */
    private StrategyVO strategy;

    /** 奖品配置 */
    private List<AwardVO> awardList;
}
