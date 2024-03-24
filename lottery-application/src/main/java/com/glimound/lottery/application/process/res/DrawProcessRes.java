package com.glimound.lottery.application.process.res;

import com.glimound.lottery.common.Result;
import com.glimound.lottery.domain.strategy.model.vo.DrawAwardVO;

/**
 * 活动抽奖结果
 * @author Glimound
 */
public class DrawProcessRes extends Result {

    private DrawAwardVO drawAwardVO;

    public DrawProcessRes(String code, String info) {
        super(code, info);
    }

    public DrawProcessRes(String code, String info, DrawAwardVO drawAwardVO) {
        super(code, info);
        this.drawAwardVO = drawAwardVO;
    }

    public DrawAwardVO getDrawAwardVO() {
        return drawAwardVO;
    }

    public void setDrawAwardVO(DrawAwardVO drawAwardVO) {
        this.drawAwardVO = drawAwardVO;
    }
}
