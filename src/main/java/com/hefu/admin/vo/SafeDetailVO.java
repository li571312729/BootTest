package com.hefu.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SafeDetailVO implements Serializable {
    //安全生产隐患主键
    private Integer safeId;
    //安全生产隐患地址
    private String safeAd;
    //安全生产隐患描述
    private String safeContent;
    //安全生产隐患图片
    private String photoUrl;
    //上传用户姓名
    private String name;
    //上传用户职务
    private String position;
    //项目名称
    private String projectName;
    //上传时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime uploadTime;
    //添加解决隐患备注
    private String remarkContent;
    //解决隐患图片
    private String remarkPhotoUrl;
    //解决时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dealTime;
    //解决人姓名
    private String handleName;
    //解决人职务
    private String handlePosition;
    //解决标志位
    private Integer dealFlag;
}
