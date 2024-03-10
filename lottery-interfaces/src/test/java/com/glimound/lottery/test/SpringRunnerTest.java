package com.glimound.lottery.test;

import com.alibaba.fastjson.JSON;
import com.glimound.lottery.common.Constants;
import com.glimound.lottery.domain.strategy.model.req.DrawReq;
import com.glimound.lottery.domain.award.model.req.GoodsReq;
import com.glimound.lottery.domain.award.model.res.DistributionRes;
import com.glimound.lottery.domain.strategy.model.res.DrawRes;
import com.glimound.lottery.domain.strategy.model.vo.DrawAwardInfo;
import com.glimound.lottery.domain.strategy.service.draw.IDrawExec;
import com.glimound.lottery.domain.award.service.factory.DistributionGoodsFactory;
import com.glimound.lottery.domain.award.service.goods.IDistributionGoods;
import com.glimound.lottery.infrastructure.dao.IActivityDao;
import com.glimound.lottery.infrastructure.dao.IStrategyDao;
import com.glimound.lottery.infrastructure.po.Activity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringRunnerTest {

    @Resource
    private IActivityDao activityDao;
    @Resource
    private IStrategyDao strategyDao;
    @Resource
    private IDrawExec drawExec;
    @Resource
    private DistributionGoodsFactory distributionGoodsFactory;


    @Test
    public void test_insert() {
        Activity activity = new Activity();
        activity.setActivityId(100001L);
        activity.setActivityName("test activity");
        activity.setActivityDesc("testing");
        activity.setBeginDateTime(new Date());
        activity.setEndDateTime(new Date());
        activity.setStockCount(100);
        activity.setTakeCount(10);
        activity.setState(0);
        activity.setCreator("Glimound");
        activityDao.insert(activity);
    }

    @Test
    public void test_select() {
        Activity activity = activityDao.getActivityById(100001L);
        log.info("测试结果：{}", JSON.toJSONString(activity));
    }

    @Test
    public void test_drawExec() {
        for (int i = 0; i < 100; i++) {
            drawExec.doDrawExec(new DrawReq("marina", 10001L));
            drawExec.doDrawExec(new DrawReq("tony", 10001L));
            drawExec.doDrawExec(new DrawReq("johnson", 10001L));
            drawExec.doDrawExec(new DrawReq("aby", 10001L));
        }
    }

    @Test
    public void test_award() {
        // 执行抽奖
        DrawRes drawRes = drawExec.doDrawExec(new DrawReq("tina", 10001L));

        // 判断抽奖结果
        Integer drawState = drawRes.getDrawState();
        if (Constants.DrawState.FAIL.getCode().equals(drawState)) {
            log.info("未中奖 DrawAwardInfo is null");
            return;
        }

        // 封装发奖参数，orderId：2109313442431 为模拟ID，需要在用户参与领奖活动时生成
        DrawAwardInfo drawAwardInfo = drawRes.getDrawAwardInfo();
        GoodsReq goodsReq = new GoodsReq(drawRes.getUId(), "2109313442431", drawAwardInfo.getAwardId(),
                drawAwardInfo.getAwardName(), drawAwardInfo.getAwardContent());

        // 根据 awardType 从抽奖工厂中获取对应的发奖服务
        IDistributionGoods distributionGoodsService = distributionGoodsFactory
                .getDistributionGoodsService(drawAwardInfo.getAwardType());
        DistributionRes distributionRes = distributionGoodsService.doDistribution(goodsReq);

        log.info("测试结果：{}", JSON.toJSONString(distributionRes));
    }

}
