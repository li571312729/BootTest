package com.hefu.sms.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hefu.admin.entity.SysUser;
import com.hefu.admin.enums.TypeEnum;
import com.hefu.admin.enums.TypeEnumUtil;
import com.hefu.admin.service.SysUserService;
import com.hefu.common.exception.BaseException;
import com.hefu.config.redis.RedisCache;
import com.hefu.sms.service.SmsService;
import com.hefu.sms.utils.AliyunSmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {
    @Autowired
    AliyunSmsUtils aliyunSmsUtils;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    RedisCache redisCache;

    @Value("${user.achievecode}")
    private String userRedisCode;

    public final String REGEX = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";

    //修改密码时获取验证码
    @Override
    public boolean getSmsCodeInUpdatePassword(String mobile) {
        checkMobile(mobile);
        //校验手机号是否注册过
        SysUser sysUserEntity = sysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getStatus, 1).eq(SysUser::getMobile, mobile)
                .last("limit 1"));
        if(null==sysUserEntity){
            throw new BaseException(999,"手机号未注册");
        }
        String code = RandomUtil.randomInt(100000, 999999) + "";
        boolean smsFlag=false;
        try {
            smsFlag = aliyunSmsUtils.sendInsertUserMsg(mobile, code);
            if(smsFlag){
                redisCache.setCacheObject(userRedisCode +"updatePassword:"+mobile, code, 60, TimeUnit.SECONDS);
            }
            return smsFlag;
        } catch (ClientException e) {
            throw new BaseException(1223,e.getErrMsg());
        }
    }

    // 注册用户时获取手机验证码
    @Override
    public boolean getCodeInRegister(String mobile) {
        checkMobile(mobile);
        //校验手机号是否注册过
        SysUser sysUserEntity = sysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getStatus, 1).eq(SysUser::getMobile, mobile)
                .last("limit 1"));
        if(null != sysUserEntity){
            throw new BaseException(999,"该手机号已注册");
        }
        String code = RandomUtil.randomInt(100000, 999999) + "";
        boolean smsFlag=false;
        try {
            smsFlag = aliyunSmsUtils.sendInsertUserMsg(mobile, code);
            if(smsFlag){
                redisCache.setCacheObject(userRedisCode +"registerPhone:"+mobile, code, 60, TimeUnit.SECONDS);
            }
            return smsFlag;
        } catch (ClientException e) {
            throw new BaseException(1223,e.getErrMsg());
        }
    }

    //校验手机验证码
    @Override
    public boolean checkCode(Integer type,String mobile,Long userId,String code) {
        if(!StringUtils.isNotBlank(code)){
            throw new BaseException(999,"验证码不能为空");
        }
        checkMobile(mobile);
        String codeFromCache = redisCache.getCacheObject(userRedisCode +TypeEnumUtil.getByCode(TypeEnum.class, type).getOrderType() + mobile);
        if(!StringUtils.isNotBlank(codeFromCache)){
            throw new BaseException(999,"请重新获取手机验证码");
        }
        boolean checkFlag = code.equals(codeFromCache);
        return checkFlag;
    }

    //获取更换手机时的验证码
    @Override
    public boolean getCodeInChangeMobile(String mobile) {
        checkMobile(mobile);
        SysUser sysUserEntity = sysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getStatus, 1).eq(SysUser::getMobile, mobile)
                .last("limit 1"));
        if(sysUserEntity == null){
            throw new BaseException(999, "手机号未注册");
        }
        String code = RandomUtil.randomInt(100000, 999999) + "";
        boolean smsFlag=false;
        try {
            smsFlag = aliyunSmsUtils.sendInsertUserMsg(mobile, code);
            if(smsFlag){
               redisCache.setCacheObject(userRedisCode +"updatePhone:"+mobile, code, 60, TimeUnit.SECONDS);
            }
            return smsFlag;
        } catch (ClientException e) {
            throw new BaseException(1223,e.getErrMsg());
        }
    }

    //校验手机号
    public void checkMobile(String mobile){
        if (!StringUtils.isNotBlank(mobile)){
            throw new BaseException(999,"手机号不能为空");
        }
        //校验手机
        Pattern p = Pattern.compile(REGEX);
        boolean b = p.matcher(mobile).matches();
        if(!b){
            throw new BaseException(999,"手机号不合法");
        }
    }
}
