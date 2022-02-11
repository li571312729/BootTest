package com.hefu.admin.dto;

import com.hefu.admin.entity.SysUser;
import com.hefu.common.utils.Md5Util;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Component
public class RegisterUserDTO {
    // 注册用户名
    @NotBlank(message = "用户名必须输入")
    @Pattern(regexp="^[\\u4E00-\\u9FA5A-Za-z][\\u4E00-\\u9FA5A-Za-z0-9_]{2,10}$", message = "用户名格式错误，请重新输入")
    private String userName;
    // 注册手机号
    @NotBlank(message = "手机号必须输入")
    @Pattern(regexp="^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$", message = "手机号格式错误，请重新输入")
    private String mobile;
    //验证码
    @NotBlank(message = "验证码必须输入")
    private String code;
    //密码
    @NotBlank(message = "密码必须输入")
    @Pattern(regexp="^(?![A-Z]+$)(?![a-z]+$)(?!\\d+$)(?![\\W_]+$){8,16}\\S+$", message = "密码格式错误，请重新输入")
    private String password;

    public SysUser convertUser() {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(userName);
        sysUser.setMobile(mobile);
        sysUser.setPassword(Md5Util.encode(password));
        sysUser.setCreateTime(new Date());
        sysUser.setStatus(1);
        return sysUser;
    }
}
