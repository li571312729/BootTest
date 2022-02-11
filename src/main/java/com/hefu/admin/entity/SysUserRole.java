package com.hefu.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@SuppressWarnings("serial")
@Data
public class SysUserRole extends Model<SysUserRole> {
    // 主键ID
    @TableId(value = "id")
    private Long id;

    // 用户ID
    @TableId(value = "user_id")
    private Long userId;

    // '权限ID'  1-超级管理员 2-管理员 3-普通用户'
    @TableId(value = "role_id")
    private Integer roleId;

    @TableId(value = "project_id")
    private String projectId;
    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }
}

