package com.hefu.common.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeVO implements Serializable {
    //姓名
    private String name;
    //组织|部门
    private String webDeptName;
    //工号
    private String employeeWorkNum;
    //职务
    private String position;
    //手机号
    private String mobile;
}
