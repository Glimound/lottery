package com.glimound.lottery.test.domain;

import com.alibaba.fastjson.JSON;
import com.glimound.lottery.domain.rule.model.req.DecisionMatterReq;
import com.glimound.lottery.domain.rule.model.res.EngineRes;
import com.glimound.lottery.domain.rule.service.engine.IEngineFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RuleTest {

    @Resource
    private IEngineFilter engineFilter;

    @Test
    public void test_process() {
        DecisionMatterReq req = new DecisionMatterReq();
        req.setTreeId(2110081902L);
        req.setUserId("glimound");
        req.setValMap(new HashMap<String, Object>() {{
            put("gender", "man");
            put("age", "25");
        }});

        EngineRes res = engineFilter.process(req);

        log.info("请求参数：{}", JSON.toJSONString(req));
        log.info("测试结果：{}", JSON.toJSONString(res));
    }

}
