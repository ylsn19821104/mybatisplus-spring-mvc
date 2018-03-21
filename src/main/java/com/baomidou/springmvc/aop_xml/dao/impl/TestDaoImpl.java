package com.baomidou.springmvc.aop_xml.dao.impl;

import com.baomidou.springmvc.aop_xml.dao.TestDao;
import lombok.Data;

@Data
public class TestDaoImpl implements TestDao {
    private String name;

    @Override
    public void select() {
        System.err.println("Enter TestDaoImpl.select()");
    }

    @Override
    public void insert() {
        System.err.println("Enter TestDaoImpl.insert()");
    }
}
