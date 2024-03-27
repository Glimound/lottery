package com.glimound.lottery.application.mq.comsumer;

import com.alibaba.fastjson.JSON;
import com.glimound.lottery.domain.activity.model.vo.ActivityPartakeRecordVO;
import com.glimound.lottery.domain.activity.service.partake.IActivityPartake;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 抽奖活动领取记录监听消息
 * @author Glimound
 */
@Component
@Slf4j
public class LotteryActivityPartakeRecordListener {

    @Resource
    private IActivityPartake activityPartake;

    @KafkaListener(topics = "lottery_activity_partake", groupId = "lottery")
    public void onMessage(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<?> message = Optional.ofNullable(record.value());

        // 1. 判断消息是否存在
        if (!message.isPresent()) {
            return;
        }

        // 2. 转化对象（或者你也可以重写Serializer<T>）
        ActivityPartakeRecordVO activityPartakeRecordVO = JSON.parseObject((String) message.get(), ActivityPartakeRecordVO.class);
        log.info("消费MQ消息，异步扣减活动库存 message：{}", message.get());

        // 3. 更新数据库库存
        activityPartake.updateActivityStock(activityPartakeRecordVO);

        // 4. 信息消费完成
        ack.acknowledge();
    }

}