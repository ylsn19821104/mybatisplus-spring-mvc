package com.baomidou.springmvc.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.springmvc.util.MQUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 订单支付成功消息监听
 */
@Component
@Slf4j
public class OrderPaySuccessConsumer {
    @Value("${mq.payment_success.group}")
    private String consumerGroup;
    @Value("${mq.payment_success.topic}")
    private String topic;
    private String tag = "*";
    @Value(value = "${mq.order.producer.name_server}")
    private String nameServer;

    @PostConstruct
    public void initialize() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(nameServer);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {
            consumer.subscribe(topic, tag);
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                MessageExt messageExt = msgs.get(0);
                try {
                    String topic = messageExt.getTopic();
                    String tags = messageExt.getTags();
                    String body = new String(messageExt.getBody());
                    log.info("OrderPaySuccessConsumer consumeMessage(): topic = [" + topic + "], tags =[" + tags + "], body = [" + body + "]");
                    JSONObject jsonObject = JSON.parseObject(body);
                    if (null != jsonObject) {
                        String paymentType = jsonObject.getString("paymentType");
                        //金额在创单的时候已经传进去了，所以这里可以不用覆盖数据库。但是后期支付金额以收到支付消息金额为准的时候，可以走这个代码
                        Long transId = jsonObject.getLong("transId");
                        List<Long> orderIdsByTransId = null;//orderPaymentMappingService.getOrderIdsByTransId(transId);
                        Long orderId = null;
                        if (!CollectionUtils.isEmpty(orderIdsByTransId)) {
                            orderId = orderIdsByTransId.get(0);
                        }

                        /**查找是否有发票记录，有则修改
                         Invoice invoice = new Invoice();
                         invoice.setOrderId(orderId);
                         Invoice getInvoice = invoiceService.get(invoice);
                         if (null != getInvoice) {
                         invoice.setPayType(parsePaymentType(paymentType));
                         Integer update = invoiceService.update(invoice);
                         if (update == 1 || update == 0) {
                         return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                         }
                         } else {
                         logger.debug("orderId = [" + orderId + "] 的发票信息不存在，不需要修改发票支付方式");
                         return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                         }*/
                    }
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                } catch (Exception e) {
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
        });
        MQUtil.startDefaultMQPushConsumer(consumer);
    }
}
