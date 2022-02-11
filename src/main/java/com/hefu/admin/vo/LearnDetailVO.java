package com.hefu.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class LearnDetailVO implements Serializable {
    //学习天地主键
    private Long learnId;
    //文档类型
    private String docType;
    //文档标题
    private String docTitle;
    //文档本地地址
    private String docUrl;
    //文档格式
    private String docFormat;
    //上传时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
