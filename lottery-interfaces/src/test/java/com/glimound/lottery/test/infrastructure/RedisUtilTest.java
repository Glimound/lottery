package com.glimound.lottery.test.infrastructure;

import com.alibaba.fastjson.JSON;
import com.glimound.lottery.common.Constants;
import com.glimound.lottery.infrastructure.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisUtilTest {

    @Resource
    private RedisUtil redisUtil;

    @Test
    public void test_set() {
        redisUtil.set("lottery_user_key", "glimound");
        redisUtil.set("good", 1234143, 10000L);
        redisUtil.setNx("test", 10000L);
        redisUtil.set(Constants.RedisKey.KEY_LOTTERY_ACTIVITY_STOCK_COUNT(100001L), 71);
    }

    @Test
    public void test_get() {
        Object user = redisUtil.get("lottery_user_key");
        log.info("测试结果：{}", JSON.toJSONString(user));
    }
}