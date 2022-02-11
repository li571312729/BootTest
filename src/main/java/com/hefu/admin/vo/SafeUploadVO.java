package com.hefu.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SafeUploadVO implements Serializable {
    //安全生产主键
    private Long safeId;
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
    //解决用户姓名
    private String dealName;
    //解决用户职务
    private String dealPosition;
    //上传时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime uploadTime;
    //解决标志位
    private Integer dealFlag;
}
