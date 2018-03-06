package com.baomidou.springmvc.model.system;

import lombok.Data;

@Data
public class Student {
    private String name;
    /**
     * 身份证号码
     */
    private String cardNo;
}
