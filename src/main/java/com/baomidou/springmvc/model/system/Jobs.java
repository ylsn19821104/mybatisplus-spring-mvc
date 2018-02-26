package com.baomidou.springmvc.model.system;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Jobs implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 公司信息
     */
    //公司全称
    private String companyFullName;
    //公司简称
    private String companyShortName;
    //公司Id
    private String companyId;
    //公司标签
    private List<String> companyLabelList;
    //公司所在城市
    private String city;
    //公司所在区
    private String district;
    //要求学历
    private String education;
    //融资阶段
    private String financeStage;
    /**
     * 职位信息
     */

    //一级分类
    private String firstType;
    //二级分类
    private String secondType;
    //发布时间
    private String formatCreateTime;
    //所属行业
    private String industryField;
    //工作性质
    private String jobNature;
    //职位Id
    private String positionId;
    //职位优势
    private String positionAdvantage;
    //职位名称
    private String positionName;
    //职位标签
    private List<String> positionLables;
    //薪资范围
    private String salary;
    //要求工作年限
    private String workYear;
}