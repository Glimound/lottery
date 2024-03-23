package com.glimound.lottery.test.domain;

import com.glimound.lottery.domain.strategy.model.vo.AwardRateVO;
import com.glimound.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DrawAlgorithmTest {
    //@Resource(name = "entiretyRateRandomDrawAlgorithm")
    @Resource(name = "singleRateRandomDrawAlgorithm")
    private IDrawAlgorithm randomDrawAlgorithm;

    @Before
    public void init() {
        // 奖品信息
        List<AwardRateVO> strategyList = new ArrayList<>();
        strategyList.add(new AwardRateVO(1L, new BigDecimal("0.05")));
        strategyList.add(new AwardRateVO(2L, new BigDecimal("0.15")));
        strategyList.add(new AwardRateVO(3L, new BigDecimal("0.20")));
        strategyList.add(new AwardRateVO(4L, new BigDecimal("0.25")));
        strategyList.add(new AwardRateVO(5L, new BigDecimal("0.35")));

        // 初始数据
        randomDrawAlgorithm.initRateTuple(100001L, strategyList);
    }

    @Test
    public void test_randomDrawAlgorithm() {

        List<Long> excludeAwardIds = new ArrayList<>();
        HashMap<Long, Integer> count = new HashMap<>();
        excludeAwardIds.add(2L);
        excludeAwardIds.add(4L);

        for (int i = 0; i < 10000; i++) {
            Long awardId = randomDrawAlgorithm.randomDraw(100001L, excludeAwardIds);
            System.out.println("中奖结果：" + awardId);
            count.put(awardId, count.computeIfAbsent(awardId, k -> 0) + 1);
        }
        System.out.println("\n中奖结果统计：" + count);

    }
}
