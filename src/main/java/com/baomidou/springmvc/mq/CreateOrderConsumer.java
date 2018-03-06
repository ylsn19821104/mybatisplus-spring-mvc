package com.baomidou.springmvc.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.springmvc.util.MQUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class CreateOrderConsumer {
    @Value("${mq.create_order.group}")
    private String consumerGroup;

    @Value("{mq.create_order.topic}")
    private String topic;

    @Value("{mq.order.producer.name_server}")
    private String nameServer;

    private String tag = "*";

    @PostConstruct
    public void init() {
        log.info("CreateOrderConsumer initialize() start ");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(nameServer);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {
            consumer.subscribe(topic, tag);
            consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
                try {
                    MessageExt messageExt = msgs.get(0);
                    String topic = messageExt.getTopic();
                    String tags = messageExt.getTags();
                    String body = new String(messageExt.getBody());
                    JSONObject jsonObject = JSON.parseObject(body);
                    log.info("收到消息：topic = [" + topic + "], tags = [" + tags + "], body = [" + body + "]");

                    if (null != jsonObject && jsonObject.containsKey("orderInvoice")) {
                        JSONObject orderInvoiceJson = jsonObject.getJSONObject("orderInvoice");
                    }

                    //保存发票
                    Integer insert = 0;//invoiceService.insert(invoice);
                    if (1 == insert || 0 == insert) {
                        log.info("保存发票结果 insert = [" + insert + "]");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    } else {
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                } catch (Exception e) {
                    log.error("保存发票错误:" + e.getMessage());
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            });
            MQUtil.startDefaultMQPushConsumer(consumer);
        } catch (MQClientException e) {
            e.printStackTrace();
        }

    }
}
