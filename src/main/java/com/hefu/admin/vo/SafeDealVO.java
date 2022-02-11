package com.hefu.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SafeDealVO implements Serializable {
    //安全生产主键
    private Long safeId;
    //是否添加解决备注0未添加1添加
    private Integer remarkFlag;
    //添加解决隐患备注
    private String remarkContent;
    //解决隐患图片
    private String remarkPhotoUrl;
    //解决时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dealTime;
    //解决人
    private String dealName;
    //解决人职务
    private String dealPosition;
}
