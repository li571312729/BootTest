package com.hefu.admin.dto;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class DealRemark {
    @NotNull(message = "安全隐患id不能为空")
    private Long safeId;
    @NotNull(message = "项目id不能为空")
    private Integer projectId;
    @Length(max = 100,message = "解决隐患备注不可超过100字")
    private String remarkContent;
    @NotNull(message = "处理备注标志位不为空")
    private Integer remarkFlag;
    private String remarkPhotoUrl;

}
