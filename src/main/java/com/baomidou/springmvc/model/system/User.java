package com.baomidou.springmvc.model.system;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.springmvc.common.SuperEntity;
import com.baomidou.springmvc.model.enums.TypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统用户表
 */
@TableName("sys_user")
@Data
@NoArgsConstructor
public class User extends SuperEntity {

    /**
     * 用户名
     */
    private String name;
    /**
     * 用户年龄
     */
    private TypeEnum type;
    /**
     * 通用枚举测试
     */
    private Integer age;
    /**
     * 自定义填充的创建时间
     */
    @TableField(fill = FieldFill.INSERT)// 该注解插入忽略验证，自动填充
    private Date ctime;

    /**
     * 一个用户只能对应一个客户
     */
    private Cust cust;

    /**
     * 一个用户可以有多个账户
     */
    private List<Acct> accts;

    private Role role;

    public User(Long id, String name) {
        setId(id);
        this.name = name;
    }
}
