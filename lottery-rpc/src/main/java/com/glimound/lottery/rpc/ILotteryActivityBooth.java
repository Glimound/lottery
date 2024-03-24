package com.glimound.lottery.rpc;

import com.glimound.lottery.rpc.req.DrawReq;
import com.glimound.lottery.rpc.req.QuantificationDrawReq;
import com.glimound.lottery.rpc.res.DrawRes;

/**
 * @author Glimound
 */
public interface ILotteryActivityBooth {

    /**
     * 指定活动抽奖
     * @param drawReq 请求参数
     * @return        抽奖结果
     */
    DrawRes doDraw(DrawReq drawReq);

    /**
     * 量化人群抽奖
     * @param quantificationDrawReq 请求参数
     * @return                      抽奖结果
     */
    DrawRes doQuantificationDraw(QuantificationDrawReq quantificationDrawReq);

}
