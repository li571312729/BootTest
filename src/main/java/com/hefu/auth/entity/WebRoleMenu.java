package com.hefu.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * (WebRoleMenu)表实体类
 *
 */
@SuppressWarnings("serial")
public class WebRoleMenu extends Model<WebRoleMenu> {
    //角色资源关联表主键
    @TableId(value = "id", type = IdType.AUTO)
    private Long roleMenuId;
    //角色id
    private Integer roleId;
    //资源id
    private Integer webMenuId;


    public Long getRoleMenuId() {
        return roleMenuId;
    }

    public void setRoleMenuId(Long roleMenuId) {
        this.roleMenuId = roleMenuId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getWebMenuId() {
        return webMenuId;
    }

    public void setWebMenuId(Integer webMenuId) {
        this.webMenuId = webMenuId;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.roleMenuId;
    }
    }