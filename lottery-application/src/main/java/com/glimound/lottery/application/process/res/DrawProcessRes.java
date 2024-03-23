package com.glimound.lottery.application.process.res;

import com.glimound.lottery.common.Result;
import com.glimound.lottery.domain.strategy.model.vo.DrawAwardInfo;

/**
 * 活动抽奖结果
 * @author Glimound
 */
public class DrawProcessRes extends Result {

    private DrawAwardInfo drawAwardInfo;

    public DrawProcessRes(String code, String info) {
        super(code, info);
    }

    public DrawProcessRes(String code, String info, DrawAwardInfo drawAwardInfo) {
        super(code, info);
        this.drawAwardInfo = drawAwardInfo;
    }

    public DrawAwardInfo getDrawAwardInfo() {
        return drawAwardInfo;
    }

    public void setDrawAwardInfo(DrawAwardInfo drawAwardInfo) {
        this.drawAwardInfo = drawAwardInfo;
    }
}
