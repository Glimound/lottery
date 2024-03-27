package com.glimound.lottery.test.interfaces;

import com.alibaba.fastjson.JSON;
import com.glimound.lottery.rpc.ILotteryActivityBooth;
import com.glimound.lottery.rpc.req.DrawReq;
import com.glimound.lottery.rpc.req.QuantificationDrawReq;
import com.glimound.lottery.rpc.res.DrawRes;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LotteryActivityBoothTest {

    @Resource
    private ILotteryActivityBooth lotteryActivityBooth;

    @Test
    public void test_doDraw() throws InterruptedException {
        DrawReq drawReq = new DrawReq();
        drawReq.setUId("glimound");
        drawReq.setActivityId(100001L);
        DrawRes drawRes = lotteryActivityBooth.doDraw(drawReq);
        log.info("请求参数：{}", JSON.toJSONString(drawReq));
        log.info("测试结果：{}", JSON.toJSONString(drawRes));
        Thread.sleep(100);
    }

    @Test
    public void test_doQuantificationDraw() {
        QuantificationDrawReq req = new QuantificationDrawReq();
        req.setUId("glimound");
        req.setTreeId(2110081902L);
        req.setValMap(new HashMap<String, Object>() {{
            put("gender", "man");
            put("age", "18");
        }});

        DrawRes drawRes = lotteryActivityBooth.doQuantificationDraw(req);
        log.info("请求参数：{}", JSON.toJSONString(req));
        log.info("测试结果：{}", JSON.toJSONString(drawRes));

    }

}