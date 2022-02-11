package com.hefu.admin.dto;

import com.hefu.admin.entity.SysUser;
import com.hefu.common.utils.Md5Util;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Component
public class ProjectUserDTO {
    // 用户名
    @NotBlank(message = "用户名必须输入")
    @Pattern(regexp="^[\\u4E00-\\u9FA5A-Za-z][\\u4E00-\\u9FA5A-Za-z0-9_]{2,10}$", message = "用户名格式错误，请重新输入")
    private String userName;
    // 手机号
    @NotBlank(message = "手机号必须输入")
    @Pattern(regexp="^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$", message = "手机号格式错误，请重新输入")
    private String mobile;
    //用户角色 1-超级管理员 2-管理员 3-普通用户
    @NotNull(message = "用户角色必须输入")
    private Integer role;
    //项目ID
    @NotBlank(message = "项目ID必须输入")
    private String projectId;
}
