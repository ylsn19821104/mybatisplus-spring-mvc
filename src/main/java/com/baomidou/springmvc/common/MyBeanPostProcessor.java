package com.baomidou.springmvc.common;

import com.baomidou.springmvc.listener.MyApplicationListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass() == MyApplicationListener.class) {
            MyApplicationListener s = (MyApplicationListener) bean;
            System.err.println("before postProcessBeforeInitialization name is:" + s.getName());
            s.setName("MyApplicationListener");
            System.err.println("before postProcessBeforeInitialization name is:" + s.getName());
            return s;
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass() == MyApplicationListener.class) {
            MyApplicationListener s = (MyApplicationListener) bean;
            System.err.println("before postProcessAfterInitialization name is:" + s.getName());
            s.setName("haha");
            System.err.println("before postProcessAfterInitialization name is:" + s.getName());
            return s;
        }
        return bean;
    }
}
