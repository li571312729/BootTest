package com.hefu.admin.dto;

import lombok.Data;

@Data
public class LearnDTO {
    //文档类型
    private String docType;
    //文档标题
    private String docTitle;
    //文档文件名
    private String docUrl;
    //1.专家资料0.学习天地2.疫情防控3.ai超算
    private Integer isExperts;
}
