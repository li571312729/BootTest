package com.hefu.auth.entity;

import java.util.ArrayList;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * (WebMenu)表实体类
 *
 */
@SuppressWarnings("serial")
@Data
public class WebMenu extends Model<WebMenu> {
    //资源id主键
    @TableId(value = "web_menu_id")
    private Integer webMenuId;
    //资源名字
    private String name;
    //父资源id
    private Integer parentId;
    //前端组件
    private String component;
    //资源路径
    private String path;
    //权限标识
    private String perms;
    //资源类型1模块2按钮
    private Integer menuType;
    //有效值
    private Integer isValid;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //备注
    private String remark;
    /** 子菜单 */
    private List<WebMenu> children = new ArrayList<WebMenu>();
    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.webMenuId;
    }
    }