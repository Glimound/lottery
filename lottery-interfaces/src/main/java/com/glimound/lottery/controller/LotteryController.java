package com.glimound.lottery.controller;

import com.glimound.lottery.rpc.ILotteryActivityBooth;
import com.glimound.lottery.rpc.req.DrawReq;
import com.glimound.lottery.rpc.res.DrawRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试用RESTful HTTP接口
 * @author Glimound
 */
@RestController
@RequestMapping("/lottery")
public class LotteryController {

    @Autowired
    private ILotteryActivityBooth lotteryActivityBooth;

    @GetMapping
    public DrawRes doDraw(@RequestParam String uId, @RequestParam Long activityId) {
        return lotteryActivityBooth.doDraw(new DrawReq(uId, activityId));
    }

}
