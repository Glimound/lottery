package com.glimound.lottery.domain.activity.service.deploy.impl;

import com.alibaba.fastjson.JSON;
import com.glimound.lottery.domain.activity.model.aggregates.ActivityConfigRich;
import com.glimound.lottery.domain.activity.model.req.ActivityConfigReq;
import com.glimound.lottery.domain.activity.model.vo.ActivityVO;
import com.glimound.lottery.domain.activity.model.vo.AwardVO;
import com.glimound.lottery.domain.activity.model.vo.StrategyDetailVO;
import com.glimound.lottery.domain.activity.model.vo.StrategyVO;
import com.glimound.lottery.domain.activity.repository.IActivityRepository;
import com.glimound.lottery.domain.activity.service.deploy.IActivityDeploy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部署活动配置服务
 * @author Glimound
 */
@Service
@Slf4j
public class ActivityDeployImpl implements IActivityDeploy {
    @Resource
    private IActivityRepository activityRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createActivity(ActivityConfigReq req) {
        log.info("创建活动配置开始，activityId：{}", req.getActivityId());

        ActivityConfigRich activityConfigRich = req.getActivityConfigRich();
        try {
            // 添加活动配置
            ActivityVO activity = activityConfigRich.getActivity();
            activityRepository.addActivity(activity);

            // 添加奖品配置
            List<AwardVO> awardList = activityConfigRich.getAwardList();
            activityRepository.addAward(awardList);

            // 添加策略配置
            StrategyVO strategy = activityConfigRich.getStrategy();
            activityRepository.addStrategy(strategy);

            // 添加策略明细配置
            List<StrategyDetailVO> strategyDetailList = activityConfigRich.getStrategy().getStrategyDetailList();
            activityRepository.addStrategyDetailList(strategyDetailList);

            log.info("创建活动配置完成，activityId：{}", req.getActivityId());
        } catch (DuplicateKeyException e) {
            log.error("创建活动配置失败，唯一索引冲突 activityId：{} reqJson：{}", req.getActivityId(), JSON.toJSONString(req), e);
            throw e;
        }

    }

    @Override
    public void updateActivity(ActivityConfigReq req) {

    }

    @Override
    public List<ActivityVO> scanToDoActivity(Long id) {
        return activityRepository.listToDoActivity(id);
    }

}
