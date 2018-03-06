package com.baomidou.springmvc.listener;


import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

import javax.annotation.PostConstruct;

@Component
@Data
public class MyApplicationListener implements ApplicationListener<ServletRequestHandledEvent>, InitializingBean {
    private String name;

    @Override
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        System.err.println("ClientAddress:" + event.getClientAddress());
        System.err.println("RequestUrl:" + event.getRequestUrl());
        System.err.println("RequestMethod:" + event.getMethod());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.err.println("测试bean初始化顺序");
        System.err.println("before InitializingBean,name is:" + name);
        this.name = "InitializingBean";
    }

    @PostConstruct
    public void init() {
        System.err.println("before PostConstruct,name is:" + name);
        this.name = "from PostConstruct";
        System.err.println("after PostConstruct,name is:" + name);
    }
}
