package com.baomidou.springmvc.aop_annotation.dao.impl;

import com.baomidou.springmvc.aop_annotation.dao.TestDao;
import org.springframework.stereotype.Component;

@Component("testDao")
public class TestDaoImpl implements TestDao {
    @Override
    public void select() {
        System.err.println("Enter TestDaoImpl.select()");
    }

    @Override
    public void insert() {
        System.err.println("Enter TestDaoImpl.insert()");
    }
}
