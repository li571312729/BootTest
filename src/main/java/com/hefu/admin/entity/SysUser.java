package com.hefu.admin.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;


/**
 * (SysUser)表实体类
 * @author Administrator
 */
@SuppressWarnings("serial")
@Data
public class SysUser extends Model<SysUser> {
    //用户id
    @TableId(value = "user_id")
    private Long userId;
    //用户名
    @TableId(value = "username")
    private String username;
    //真实姓名
    private String name;
    //密码
    private String password;
    //部门编号
    private String webDeptId;
    //工号
    private String employeeWorkNum;
    //邮箱
    private String email;
    //手机号
    @TableId(value = "mobile")
    private String mobile;
    //状态 0:禁用，1:正常
    private Integer status;
    //所在公司
    private String company;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.userId;
    }
    }
