package com.hefu.admin.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor  // 必须要有空的构造方法，java反射需要空的构造方法  这个easypoi发射只取了空的构造方法，有点bug，所以自己重载了构造方法后，要加上空的构造方法
@EqualsAndHashCode(callSuper = false)
public class UserDto {

    @Excel(name = "userId", width = 10)
    private Long userId;

    @Excel(name = "角色名称", width = 20)
    private String roleName;

    @Excel(name = "角色标识", width = 20)
    private String roleSign;

    //用户名
    @Excel(name = "用户名", width = 20, needMerge = true)
    private String username;

    //真实姓名
    @Excel(name = "真实姓名", width = 20, needMerge = true)
    private String name;

    //密码
    @Excel(name = "密码", width = 20, desensitizationRule = "0_0")
    private String password;

    //部门名称
    @Excel(name = "部门名称", width = 10)
    private String webDeptName;

    //工号
    @Excel(name = "工号", width = 15)
    private String employeeWorkNum;

    //邮箱
    @Excel(name = "邮箱", width = 20)
    private String email;

    //手机号
    @Excel(name = "手机号", width = 20, needMerge = true, desensitizationRule = "3_4")
    private String mobile;

    //状态 0:禁用，1:正常
    @Excel(name = "状态", width = 10, replace = {"禁用_0", "正常_1"})
    private Integer status;

    //所在公司
    @Excel(name = "所在公司", width = 10)
    private String company;

    //创建时间
    @Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    //修改时间
    @Excel(name = "最后修改时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
