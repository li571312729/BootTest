package com.hefu.admin.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * (WebDept)表实体类
 *
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
public class WebDept extends Model<WebDept> {
    //部门主键
    @TableId(value = "web_dept_id",type = IdType.AUTO)
    private Integer webDeptId;
    //部门名称
    private String webDeptName;
    //部门备注
    private String webDeptRemark;
    //0项目外1项目内
    private Integer deptFlag;
    //有效值
    private Integer isValid;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.webDeptId;
    }
    }