package com.glimound.lottery.test.dao;

import com.alibaba.fastjson.JSON;
import com.glimound.lottery.infrastructure.dao.IActivityDao;
import com.glimound.lottery.infrastructure.po.Activity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ActivityDaoTest {


    @Resource
    private IActivityDao activityDao;

    @Test
    public void test_getActivityById() {
        Activity activity = activityDao.getActivityById(100001L);
        log.info("测试结果：{}", JSON.toJSONString(activity));
    }

}