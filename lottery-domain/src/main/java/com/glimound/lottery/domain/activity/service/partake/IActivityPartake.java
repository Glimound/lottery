package com.glimound.lottery.domain.activity.service.partake;

import com.glimound.lottery.common.Result;
import com.glimound.lottery.domain.activity.model.req.PartakeReq;
import com.glimound.lottery.domain.activity.model.res.PartakeRes;
import com.glimound.lottery.domain.activity.model.vo.DrawOrderVO;

/**
 * 抽奖活动参与接口
 * @author Glimound
 */
public interface IActivityPartake {

    /**
     * 参与活动
     * @param req 入参
     * @return    领取结果
     */
    PartakeRes doPartake(PartakeReq req);

    /**
     * 保存奖品单
     * @param drawOrder 奖品单
     * @return          保存结果
     */
    Result recordDrawOrder(DrawOrderVO drawOrder);

}
