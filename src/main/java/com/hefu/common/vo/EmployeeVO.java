package com.hefu.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeVO implements Serializable {
    //员工姓名
    private String name;
    //部门名
    private String webDeptName;
    //职位
    private String position;
    //工号
    private String employeeWorkNum;
    //手机号
    private String mobile;
}
