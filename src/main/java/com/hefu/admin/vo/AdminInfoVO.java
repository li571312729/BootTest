package com.hefu.admin.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息
 */
@Data
public class AdminInfoVO implements Serializable {
    //用户id
    private Long userId;
    //真实姓名
    private String name;
    //部门名称
    private String webDeptName;
    //工号
    private String employeeWorkNum;
    //邮箱
    private String email;
    //手机号
    private String mobile;
    //所在公司
    private String company;
    //职务
    private String position;
}
