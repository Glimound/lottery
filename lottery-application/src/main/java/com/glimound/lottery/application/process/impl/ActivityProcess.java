package com.glimound.lottery.application.process.impl;

import com.glimound.lottery.application.process.IActivityProcess;
import com.glimound.lottery.application.process.req.DrawProcessReq;
import com.glimound.lottery.application.process.res.DrawProcessRes;
import com.glimound.lottery.common.Constants;
import com.glimound.lottery.domain.activity.model.req.PartakeReq;
import com.glimound.lottery.domain.activity.model.res.PartakeRes;
import com.glimound.lottery.domain.activity.model.vo.DrawOrderVO;
import com.glimound.lottery.domain.activity.service.partake.IActivityPartake;
import com.glimound.lottery.domain.strategy.model.req.DrawReq;
import com.glimound.lottery.domain.strategy.model.res.DrawRes;
import com.glimound.lottery.domain.strategy.model.vo.DrawAwardVO;
import com.glimound.lottery.domain.strategy.service.draw.IDrawExec;
import com.glimound.lottery.domain.support.ids.IIdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 活动抽奖流程编排
 * @author Glimound
 */
@Service
public class ActivityProcess implements IActivityProcess {

    @Resource
    private IActivityPartake activityPartake;
    @Resource
    private IDrawExec drawExec;
    @Resource
    private Map<Constants.Ids, IIdGenerator> idGeneratorMap;

    @Override
    public DrawProcessRes doDrawProcess(DrawProcessReq req) {
        // 领取活动单
        PartakeRes partakeRes = activityPartake.doPartake(new PartakeReq(req.getUId(), req.getActivityId(), req.getPartakeDate()));
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(partakeRes.getCode())) {
            return new DrawProcessRes(partakeRes.getCode(), partakeRes.getInfo());
        }
        Long strategyId = partakeRes.getStrategyId();
        Long takeId = partakeRes.getTakeId();

        // 执行抽奖
        DrawRes drawRes = drawExec.doDrawExec(new DrawReq(req.getUId(), strategyId, String.valueOf(takeId)));
        if (Constants.DrawState.FAIL.getCode().equals(drawRes.getDrawState())) {
            return new DrawProcessRes(Constants.ResponseCode.LOSING_DRAW.getCode(), Constants.ResponseCode.LOSING_DRAW.getInfo());
        }
        DrawAwardVO drawAwardInfo = drawRes.getDrawAwardVO();

        // 结果落库
        activityPartake.recordDrawOrder(buildDrawOrderVO(req, strategyId, takeId, drawAwardInfo));

        // 发送MQ，触发发奖流程

        // 返回结果
        return new DrawProcessRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo(), drawAwardInfo);
    }

    private DrawOrderVO buildDrawOrderVO(DrawProcessReq req, Long strategyId, Long takeId, DrawAwardVO drawAwardInfo) {
        DrawOrderVO drawOrderVO = new DrawOrderVO();
        BeanUtils.copyProperties(req, drawOrderVO);
        BeanUtils.copyProperties(drawAwardInfo, drawOrderVO);
        drawOrderVO.setStrategyId(strategyId);
        drawOrderVO.setTakeId(takeId);
        drawOrderVO.setOrderId(idGeneratorMap.get(Constants.Ids.SnowFlake).nextId());
        drawOrderVO.setGrantState(Constants.GrantState.INIT.getCode());
        return drawOrderVO;
    }

}
