package com.hefu.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (ProjectInformation)表实体类
 *
 * @author mengkai
 * @since 2020-06-29 11:02:26
 */
@SuppressWarnings("serial")
@Data
public class ProjectInformation extends Model<ProjectInformation> {
    /**
     * 项目编号
     */
    @TableId(value = "project_id", type = IdType.AUTO)
    private Integer projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 项目类型
     */
    private String projectType;
    /**
     * 项目造价(万)
     */
    private String projectCost;
    /**
     * 项目面积(㎡)
     */
    private String projectAera;
    /**
     * 计划施工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime planDate;
    /**
     * 计划竣工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime planFinDate;
    /**
     * 校验
     */
    private Integer isVaild;
    /**
     * 施工单位
     */
    private String consUnit;
    /**
     * 建设单位
     */
    private String devpUnit;
    /**
     * 监理单位
     */
    private String versionUnit;
    /**
     * 设计单位
     */
    private String designUnit;
    /**
     * 勘察单位
     */
    private String surveyUnit;
    /**
     * 项目所在地
     */
    private String projectAd;
    /**
     * 省份
     */
    private String province;
    /**
     * 项目地址简写
     */
    private String projectThumAd;
    /**
     * 地图坐标
     */
    private String projectMap;
    /**
     * 项目详情
     */
    private String projectInfo;
    /**
     * 项目全景图
     */
    private Long figure;
    /**
     * 视频文件id
     */
    private Long videoId;
    /**
     * 未监测;监测中;监测完成
     */
    private String projectCompletion;
    /**
     * 项目经理
     */
    private String projectMananger;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 创建人
     */
    private String userId;
    /**
     * mac地址
     */
    private String macAd;
    /**
     * 预留属性
     */
    @TableField(exist = false)
    private String filed04;
    /**
     * 项目所在地区
     */
    @TableField(exist = false)
    private String area;
    /**
     * 项目所在地区
     */
    @TableField(exist = false)
    private String projectNumber;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.projectId;
    }


}
