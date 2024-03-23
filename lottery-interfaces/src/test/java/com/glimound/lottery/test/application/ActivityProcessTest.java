package com.glimound.lottery.test.application;

import com.alibaba.fastjson.JSON;
import com.glimound.lottery.application.process.IActivityProcess;
import com.glimound.lottery.application.process.req.DrawProcessReq;
import com.glimound.lottery.application.process.res.DrawProcessRes;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ActivityProcessTest {

    @Resource
    private IActivityProcess activityProcess;

    @Test
    public void test_doDrawProcess() throws ParseException {
        DrawProcessReq req = new DrawProcessReq("glimound", 100001L,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2024-03-15 12:30:05"));

        DrawProcessRes drawProcessRes = activityProcess.doDrawProcess(req);

        log.info("请求入参：{}", JSON.toJSONString(req));
        log.info("测试结果：{}", JSON.toJSONString(drawProcessRes));
    }

}
