package com.baomidou.springmvc.aop_annotation;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {
    @Pointcut("execution(* com.baomidou.springmvc.aop_annotation.dao.*.*(..))")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void before() {
        System.err.println("Current time:" + System.currentTimeMillis());
    }

    @After("pointCut()")
    public void after() {
        System.err.println("Current time:" + System.currentTimeMillis());
    }
}
