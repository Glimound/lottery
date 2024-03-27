package com.glimound.lottery.interfaces.facade;

import com.alibaba.fastjson.JSON;
import com.glimound.lottery.application.process.IActivityProcess;
import com.glimound.lottery.application.process.req.DrawProcessReq;
import com.glimound.lottery.application.process.res.DrawProcessRes;
import com.glimound.lottery.application.process.res.RuleQuantificationCrowdRes;
import com.glimound.lottery.common.Constants;
import com.glimound.lottery.domain.rule.model.req.DecisionMatterReq;
import com.glimound.lottery.domain.strategy.model.vo.DrawAwardVO;
import com.glimound.lottery.rpc.ILotteryActivityBooth;
import com.glimound.lottery.rpc.dto.AwardDTO;
import com.glimound.lottery.rpc.req.DrawReq;
import com.glimound.lottery.rpc.req.QuantificationDrawReq;
import com.glimound.lottery.rpc.res.DrawRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 抽奖活动展台
 * @author Glimound
 */
@Controller
@Slf4j
public class LotteryActivityBooth implements ILotteryActivityBooth {
    @Resource
    private IActivityProcess activityProcess;

    @Override
    public DrawRes doDraw(DrawReq drawReq) {
        try {
            log.info("抽奖，开始 uId：{} activityId：{}", drawReq.getUId(), drawReq.getActivityId());

            // 1. 执行抽奖
            DrawProcessRes drawProcessRes = activityProcess.doDrawProcess(
                    new DrawProcessReq(drawReq.getUId(), drawReq.getActivityId(), new Date()));
            if (!Constants.ResponseCode.SUCCESS.getCode().equals(drawProcessRes.getCode())) {
                log.error("抽奖，失败(抽奖过程异常) uId：{} activityId：{}", drawReq.getUId(), drawReq.getActivityId());
                return new DrawRes(drawProcessRes.getCode(), drawProcessRes.getInfo());
            }

            // 2. 数据转换
            DrawAwardVO drawAwardVO = drawProcessRes.getDrawAwardVO();
            AwardDTO awardDTO = new AwardDTO();
            BeanUtils.copyProperties(drawAwardVO, awardDTO);
            awardDTO.setActivityId(drawReq.getActivityId());

            // 3. 封装数据
            DrawRes drawRes = new DrawRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
            drawRes.setAwardDTO(awardDTO);

            log.info("抽奖，完成 uId：{} activityId：{} drawRes：{}", drawReq.getUId(), drawReq.getActivityId(), JSON.toJSONString(drawRes));

            return drawRes;
        } catch (Exception e) {
            log.error("抽奖，失败 uId：{} activityId：{} reqJson：{}", drawReq.getUId(), drawReq.getActivityId(), JSON.toJSONString(drawReq), e);
            return new DrawRes(Constants.ResponseCode.UNKNOWN_ERROR.getCode(), Constants.ResponseCode.UNKNOWN_ERROR.getInfo());
        }
    }

    @Override
    public DrawRes doQuantificationDraw(QuantificationDrawReq quantificationDrawReq) {
        try {
            log.info("量化人群抽奖，开始 uId：{} treeId：{}", quantificationDrawReq.getUId(), quantificationDrawReq.getTreeId());

            // 1. 执行规则引擎，获取用户可以参与的活动号
            RuleQuantificationCrowdRes ruleQuantificationCrowdRes = activityProcess.doRuleQuantificationCrowd(
                    new DecisionMatterReq(quantificationDrawReq.getTreeId(), quantificationDrawReq.getUId(), quantificationDrawReq.getValMap()));
            if (!Constants.ResponseCode.SUCCESS.getCode().equals(ruleQuantificationCrowdRes.getCode())) {
                log.error("量化人群抽奖，失败(规则引擎执行异常) uId：{} treeId：{}", quantificationDrawReq.getUId(), quantificationDrawReq.getTreeId());
                return new DrawRes(ruleQuantificationCrowdRes.getCode(), ruleQuantificationCrowdRes.getInfo());
            }

            // 2. 执行抽奖
            Long activityId = ruleQuantificationCrowdRes.getActivityId();
            DrawProcessRes drawProcessRes = activityProcess.doDrawProcess(
                    new DrawProcessReq(quantificationDrawReq.getUId(), activityId, new Date()));
            if (!Constants.ResponseCode.SUCCESS.getCode().equals(drawProcessRes.getCode())) {
                log.error("量化人群抽奖，失败(抽奖过程异常) uId：{} treeId：{}", quantificationDrawReq.getUId(), quantificationDrawReq.getTreeId());
                return new DrawRes(drawProcessRes.getCode(), drawProcessRes.getInfo());
            }

            // 3. 数据转换
            DrawAwardVO drawAwardVO = drawProcessRes.getDrawAwardVO();
            AwardDTO awardDTO = new AwardDTO();
            BeanUtils.copyProperties(drawAwardVO, awardDTO);
            awardDTO.setActivityId(activityId);

            // 4. 封装数据
            DrawRes drawRes = new DrawRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
            drawRes.setAwardDTO(awardDTO);

            log.info("量化人群抽奖，完成 uId：{} treeId：{} drawRes：{}", quantificationDrawReq.getUId(), quantificationDrawReq.getTreeId(), JSON.toJSONString(drawRes));

            return drawRes;
        } catch (Exception e) {
            log.error("量化人群抽奖，失败 uId：{} treeId：{} reqJson：{}", quantificationDrawReq.getUId(), quantificationDrawReq.getTreeId(), JSON.toJSONString(quantificationDrawReq), e);
            return new DrawRes(Constants.ResponseCode.UNKNOWN_ERROR.getCode(), Constants.ResponseCode.UNKNOWN_ERROR.getInfo());
        }
    }

}
