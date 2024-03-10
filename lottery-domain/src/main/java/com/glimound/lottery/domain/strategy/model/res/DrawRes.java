package com.glimound.lottery.domain.strategy.model.res;

import com.glimound.lottery.common.Constants;
import com.glimound.lottery.domain.strategy.model.vo.DrawAwardInfo;
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
    /**
     * 用户ID
     */
    private String uId;

    /**
     * 策略ID
     */
    private Long strategyId;

    /**
     * 中奖状态：0未中奖、1已中奖、2兜底奖
     */
    private Integer drawState = Constants.DrawState.FAIL.getCode();

    /**
     * 中奖奖品信息
     */
    private DrawAwardInfo drawAwardInfo;

    public DrawRes(String uId, Long strategyId, Integer drawState) {
        this.uId = uId;
        this.strategyId = strategyId;
        this.drawState = drawState;
    }
}
