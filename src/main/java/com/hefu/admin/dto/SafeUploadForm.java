package com.hefu.admin.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
public class SafeUploadForm {
    //安全生产隐患地址
    @NotNull(message = "位置不能为空")
    @NotEmpty(message = "位置请传值")
    private String safeAd;
    //安全生产隐患描述
    @NotNull(message = "内容不能为空")
    @NotEmpty(message = "内容请传值")
    @Length(max = 100,message = "字数不超过100")
    private String safeContent;
    //安全生产隐患图片
    private String photoUrl;
    @NotNull(message = "项目id不能为空")
    private Integer projectId;
}
