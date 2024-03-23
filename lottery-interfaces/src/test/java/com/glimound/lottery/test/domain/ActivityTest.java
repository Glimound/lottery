package com.glimound.lottery.test.domain;

import com.alibaba.fastjson.JSON;
import com.glimound.lottery.common.Constants;
import com.glimound.lottery.domain.activity.model.aggregates.ActivityConfigRich;
import com.glimound.lottery.domain.activity.model.req.ActivityConfigReq;
import com.glimound.lottery.domain.activity.model.req.PartakeReq;
import com.glimound.lottery.domain.activity.model.res.PartakeRes;
import com.glimound.lottery.domain.activity.model.vo.ActivityVO;
import com.glimound.lottery.domain.activity.model.vo.AwardVO;
import com.glimound.lottery.domain.activity.model.vo.StrategyDetailVO;
import com.glimound.lottery.domain.activity.model.vo.StrategyVO;
import com.glimound.lottery.domain.activity.service.deploy.IActivityDeploy;
import com.glimound.lottery.domain.activity.service.partake.IActivityPartake;
import com.glimound.lottery.domain.activity.service.stateflow.IStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ActivityTest {


    @Resource
    private IActivityDeploy activityDeploy;
    @Resource
    private IStateHandler stateHandler;
    @Resource
    private IActivityPartake activityPartake;

    private ActivityConfigRich activityConfigRich;

    /**
     * TODO：后面编写ID生成策略
     */
    private Long activityId = 120981321L;

    @Before
    public void init() {

        ActivityVO activity = new ActivityVO();
        activity.setActivityId(activityId);
        activity.setActivityName("Test Activity");
        activity.setActivityDesc("Test Activity Description");
        activity.setBeginDateTime(new Date());
        activity.setEndDateTime(new Date());
        activity.setStockCount(100);
        activity.setTakeCount(10);
        activity.setStrategyId(10001L);
        activity.setState(Constants.ActivityState.EDIT.getCode());
        activity.setCreator("glimound");

        StrategyVO strategy = new StrategyVO();
        strategy.setStrategyId(10002L);
        strategy.setStrategyDesc("Lottery Strategy");
        strategy.setStrategyMode(Constants.StrategyMode.SINGLE.getCode());
        strategy.setGrantType(1);
        strategy.setGrantDate(new Date());
        strategy.setExtInfo("");

        StrategyDetailVO strategyDetail_01 = new StrategyDetailVO();
        strategyDetail_01.setStrategyId(strategy.getStrategyId());
        strategyDetail_01.setAwardId(101L);
        strategyDetail_01.setAwardName("First Prize");
        strategyDetail_01.setAwardCount(10);
        strategyDetail_01.setAwardSurplusCount(10);
        strategyDetail_01.setAwardRate(new BigDecimal("0.05"));

        StrategyDetailVO strategyDetail_02 = new StrategyDetailVO();
        strategyDetail_02.setStrategyId(strategy.getStrategyId());
        strategyDetail_02.setAwardId(102L);
        strategyDetail_02.setAwardName("Second Prize");
        strategyDetail_02.setAwardCount(20);
        strategyDetail_02.setAwardSurplusCount(20);
        strategyDetail_02.setAwardRate(new BigDecimal("0.15"));

        StrategyDetailVO strategyDetail_03 = new StrategyDetailVO();
        strategyDetail_03.setStrategyId(strategy.getStrategyId());
        strategyDetail_03.setAwardId(103L);
        strategyDetail_03.setAwardName("Third Prize");
        strategyDetail_03.setAwardCount(50);
        strategyDetail_03.setAwardSurplusCount(50);
        strategyDetail_03.setAwardRate(new BigDecimal("0.20"));

        StrategyDetailVO strategyDetail_04 = new StrategyDetailVO();
        strategyDetail_04.setStrategyId(strategy.getStrategyId());
        strategyDetail_04.setAwardId(104L);
        strategyDetail_04.setAwardName("Fourth Prize");
        strategyDetail_04.setAwardCount(100);
        strategyDetail_04.setAwardSurplusCount(100);
        strategyDetail_04.setAwardRate(new BigDecimal("0.25"));

        StrategyDetailVO strategyDetail_05 = new StrategyDetailVO();
        strategyDetail_05.setStrategyId(strategy.getStrategyId());
        strategyDetail_05.setAwardId(105L);
        strategyDetail_05.setAwardName("Fifth Prize");
        strategyDetail_05.setAwardCount(500);
        strategyDetail_05.setAwardSurplusCount(500);
        strategyDetail_05.setAwardRate(new BigDecimal("0.35"));

        List<StrategyDetailVO> strategyDetailList = new ArrayList<>();
        strategyDetailList.add(strategyDetail_01);
        strategyDetailList.add(strategyDetail_02);
        strategyDetailList.add(strategyDetail_03);
        strategyDetailList.add(strategyDetail_04);
        strategyDetailList.add(strategyDetail_05);

        strategy.setStrategyDetailList(strategyDetailList);

        AwardVO award_01 = new AwardVO();
        award_01.setAwardId(101L);
        award_01.setAwardType(Constants.AwardType.DESC.getCode());
        award_01.setAwardName("Computer");
        award_01.setAwardContent("Please contact the event organizer");

        AwardVO award_02 = new AwardVO();
        award_02.setAwardId(102L);
        award_02.setAwardType(Constants.AwardType.DESC.getCode());
        award_02.setAwardName("Phone");
        award_02.setAwardContent("Please contact the event organizer");

        AwardVO award_03 = new AwardVO();
        award_03.setAwardId(103L);
        award_03.setAwardType(Constants.AwardType.DESC.getCode());
        award_03.setAwardName("Tablet");
        award_03.setAwardContent("Please contact the event organizer");

        AwardVO award_04 = new AwardVO();
        award_04.setAwardId(104L);
        award_04.setAwardType(Constants.AwardType.DESC.getCode());
        award_04.setAwardName("Headphones");
        award_04.setAwardContent("Please contact the event organizer");

        AwardVO award_05 = new AwardVO();
        award_05.setAwardId(105L);
        award_05.setAwardType(Constants.AwardType.DESC.getCode());
        award_05.setAwardName("Data Cable");
        award_05.setAwardContent("Please contact the event organizer");

        List<AwardVO> awardList = new ArrayList<>();
        awardList.add(award_01);
        awardList.add(award_02);
        awardList.add(award_03);
        awardList.add(award_04);
        awardList.add(award_05);

        activityConfigRich = new ActivityConfigRich(activity, strategy, awardList);

    }

    @Test
    public void test_createActivity() {
        activityDeploy.createActivity(new ActivityConfigReq(activityId, activityConfigRich));
    }

    @Test
    public void test_alterState() {
        log.info("提交审核，测试：{}", JSON.toJSONString(stateHandler.arraignment(100001L, Constants.ActivityState.EDIT)));
        log.info("审核通过，测试：{}", JSON.toJSONString(stateHandler.checkPass(100001L, Constants.ActivityState.ARRAIGNMENT)));
        log.info("运行活动，测试：{}", JSON.toJSONString(stateHandler.doing(100001L, Constants.ActivityState.PASS)));
        log.info("二次提审，测试：{}", JSON.toJSONString(stateHandler.checkPass(100001L, Constants.ActivityState.EDIT)));
    }

    @Test
    public void test_activityPartake() throws ParseException {
        PartakeReq req = new PartakeReq("Uhdgkw766120d", 100001L,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2024-03-15 12:30:05"));
        PartakeRes res = activityPartake.doPartake(req);
        log.info("请求参数：{}", JSON.toJSONString(req));
        log.info("测试结果：{}", JSON.toJSONString(res));
    }
}
