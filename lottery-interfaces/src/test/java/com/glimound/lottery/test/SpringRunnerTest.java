package com.glimound.lottery.test;

import com.alibaba.fastjson.JSON;
import com.glimound.lottery.domain.strategy.model.req.DrawReq;
import com.glimound.lottery.domain.strategy.service.draw.IDrawExec;
import com.glimound.lottery.infrastructure.dao.IActivityDao;
import com.glimound.lottery.infrastructure.dao.IStrategyDao;
import com.glimound.lottery.infrastructure.po.Activity;
import com.glimound.lottery.infrastructure.po.Strategy;
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

}
