package com.glimound.lottery.application.worker;

import com.alibaba.fastjson.JSON;
import com.glimound.db.router.strategy.IDBRouterStrategy;
import com.glimound.lottery.application.mq.producer.KafkaProducer;
import com.glimound.lottery.common.Constants;
import com.glimound.lottery.common.Result;
import com.glimound.lottery.domain.activity.model.vo.ActivityVO;
import com.glimound.lottery.domain.activity.model.vo.InvoiceVO;
import com.glimound.lottery.domain.activity.service.deploy.IActivityDeploy;
import com.glimound.lottery.domain.activity.service.partake.IActivityPartake;
import com.glimound.lottery.domain.activity.service.stateflow.IStateHandler;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * 抽奖业务，任务配置
 * @author Glimound
 */
@Component
@Slf4j
public class LotteryXxlJob {

    @Resource
    private IActivityDeploy activityDeploy;
    @Resource
    private IStateHandler stateHandler;
    @Resource
    private IDBRouterStrategy dbRouter;
    @Resource
    private IActivityPartake activityPartake;
    @Resource
    private KafkaProducer kafkaProducer;


    @XxlJob("lotteryActivityStateJobHandler")
    public void lotteryActivityStateJobHandler() throws Exception {
        log.info("扫描活动状态 Begin");

        List<ActivityVO> activityVOList = activityDeploy.scanToDoActivity(0L);
        if (activityVOList.isEmpty()){
            log.info("扫描活动状态 End 暂无符合需要扫描的活动列表");
            return;
        }

        while (!activityVOList.isEmpty()) {
            for (ActivityVO activityVO : activityVOList) {
                Integer state = activityVO.getState();
                switch (state) {
                    // 活动状态为审核通过，在临近活动开启时间前，审核活动为活动中。在使用活动的时候，需要依照活动状态核时间两个字段进行判断和使用。
                    case 4:
                        Result state4Result = stateHandler.doing(activityVO.getActivityId(), Constants.ActivityState.PASS);
                        log.info("扫描活动状态为活动中 结果：{} activityId：{} activityName：{} creator：{}", JSON.toJSONString(state4Result), activityVO.getActivityId(), activityVO.getActivityName(), activityVO.getCreator());
                        break;
                    // 扫描时间已过期的活动，从活动中状态变更为关闭状态【这里也可以细化为2个任务来处理，也可以把时间判断放到数据库中操作】
                    case 5:
                        if (activityVO.getEndDateTime().before(new Date())){
                            Result state5Result = stateHandler.close(activityVO.getActivityId(), Constants.ActivityState.DOING);
                            log.info("扫描活动状态为关闭 结果：{} activityId：{} activityName：{} creator：{}", JSON.toJSONString(state5Result), activityVO.getActivityId(), activityVO.getActivityName(), activityVO.getCreator());
                        }
                        break;
                    default:
                        break;
                }
            }
            // 获取集合中最后一条记录，继续扫描后面10条记录
            ActivityVO activityVO = activityVOList.get(activityVOList.size() - 1);
            activityVOList = activityDeploy.scanToDoActivity(activityVO.getId());
        }
        log.info("扫描活动状态 End");
    }

    @XxlJob("lotteryOrderMqStateJobHandler")
    public void lotteryOrderMqStateJobHandler() throws Exception {
        // 验证参数
        String jobParam = XxlJobHelper.getJobParam();
        if (jobParam == null) {
            log.info("扫描用户抽奖奖品发放MQ状态错误 params is null");
            return;
        }

        // 获取分布式任务配置参数信息 参数配置格式：1,2,3 也可以是指定扫描一个，也可以配置多个库，按照部署的任务集群进行数量配置，均摊分别扫描效率更高
        String[] params = jobParam.split(",");
        if (params.length == 0) {
            log.info("扫描用户抽奖奖品发放MQ状态错误 params is null");
            return;
        }
        log.info("扫描用户抽奖奖品发放MQ状态开始 params：{}", JSON.toJSONString(params));

        // 获取分库分表配置下的分表数
        int tbCount = dbRouter.tbCount();

        // 循环获取指定扫描库
        for (String param : params) {
            // 获取当前任务扫描的指定分库
            int dbCount = Integer.parseInt(param);
            // 判断配置指定扫描库数，是否存在
            if (dbCount > dbRouter.dbCount()) {
                log.info("扫描用户抽奖奖品发放MQ状态错误 db{} not exist", dbCount);
                continue;
            }

            // 循环扫描对应表
            for (int i = 1; i <= tbCount; i++) {
                // 扫描库表数据
                List<InvoiceVO> invoiceVOList = activityPartake.listFailureMqState(dbCount, i);
                log.info("扫描用户抽奖奖品发放MQ状态扫描库：{} 扫描表：{} 目标数：{}", dbCount, i, invoiceVOList.size());

                // 补偿 MQ 消息
                for (InvoiceVO invoiceVO : invoiceVOList) {
                    ListenableFuture<SendResult<String, Object>> future = kafkaProducer.sendLotteryInvoice(invoiceVO);
                    future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
                        @Override
                        public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                            // MQ 消息发送完成，更新数据库表 user_strategy_export.mq_state = 1
                            activityPartake.updateMqState(invoiceVO.getUId(), invoiceVO.getOrderId(), Constants.MQState.COMPLETE.getCode());
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            // MQ 消息发送失败，更新数据库表 user_strategy_export.mq_state = 2，等待定时任务扫码补偿MQ消息
                            activityPartake.updateMqState(invoiceVO.getUId(), invoiceVO.getOrderId(), Constants.MQState.FAIL.getCode());
                        }
                    });
                }
            }
        }
        log.info("扫描用户抽奖奖品发放MQ状态完成 param：{}", JSON.toJSONString(params));

    }
}
