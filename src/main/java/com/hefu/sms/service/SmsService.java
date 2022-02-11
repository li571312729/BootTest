package com.hefu.sms.service;

public interface SmsService {
    //修改密码时获取验证码
    boolean getSmsCodeInUpdatePassword(String mobile);

    //校验手机验证码
    boolean checkCode(Integer type, String mobile, Long userId, String code);

    //获取更换手机的验证码
    boolean getCodeInChangeMobile(String mobile);

    //注册用户时获取手机验证码
    boolean getCodeInRegister(String mobile);
}
