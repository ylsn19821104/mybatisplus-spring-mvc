package com.baomidou.springmvc.util;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;

public class MQUtil {
    public static void startDefaultMQPushConsumer(DefaultMQPushConsumer consumer) {
        if (null == consumer) {
            throw new RuntimeException("consumer not allow null");
        }
        int i = 0;
        while (true) {
            try {
                consumer.start();
                break;
            } catch (Exception ex) {
                ex.printStackTrace();
                try {
                    Thread.sleep(10000 + (i * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}