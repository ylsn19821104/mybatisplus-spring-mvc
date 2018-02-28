package com.baomidou.springmvc.listener;


import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

@Component
public class MyApplicationListener implements ApplicationListener<ServletRequestHandledEvent> {
    @Override
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        System.err.println("ClientAddress:" + event.getClientAddress());
        System.err.println("RequestUrl:" + event.getRequestUrl());
        System.err.println("RequestMethod:" + event.getMethod());
    }
}
