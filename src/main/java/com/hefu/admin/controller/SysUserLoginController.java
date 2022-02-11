package com.hefu.admin.controller;

import cn.hutool.core.util.RandomUtil;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hefu.admin.dto.RegisterUserDTO;
import com.hefu.admin.entity.SysUser;
import com.hefu.admin.enums.TypeEnum;
import com.hefu.admin.enums.TypeEnumUtil;
import com.hefu.admin.service.SysUserService;
import com.hefu.auth.security.entity.SelfUserEntity;
import com.hefu.auth.security.service.SelfUserDetailsService;
import com.hefu.common.entity.Result;
import com.hefu.common.exception.BaseException;
import com.hefu.common.utils.Md5Util;
import com.hefu.config.api.RequestLimit;
import com.hefu.config.redis.RedisCache;
import com.hefu.sms.service.SmsService;
import com.hefu.sms.utils.AliyunSmsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


/**
 * (SysUserLogin)表控制层
 * @author Administrator
 */
@Api(tags = "用户登陆模块")
@RestController
@RequestMapping("user")
@Slf4j
public class SysUserLoginController {

    @Autowired
    AliyunSmsUtils aliyunSmsUtils;

    @Autowired
    private SelfUserDetailsService selfUserDetailsService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SmsService smsService;

    @Value("${user.achievecode}")
    private String userRedisCode;

    public final String PHONE_REGEX = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
    public final String PASSWORD_REGEX = "^(?![A-Z]+$)(?![a-z]+$)(?!\\d+$)(?![\\W_]+$){8,16}\\S+$";

    @ApiOperation("用户手机号,密码登录")
    @PostMapping("login")
    public void userLogin(@RequestParam String mobile, @RequestParam String password) {
    }

    @ApiOperation("用户手机号,验证码登录")
    @PostMapping("/login/mobile")
    public void mobileLogin(@RequestParam String mobile, @RequestParam String code) {
    }

    @RequestLimit(second = 120, maxCount = 5, intervalTime = 60 * 15)
    @ApiOperation("获取登陆时手机验证码")
    @GetMapping("sms/code")
    public Result achieveMobileCode(@RequestParam String mobile) {
        if (null == mobile) {
            return Result.error("手机号不能为空");
        }

        //校验手机
        Pattern p = Pattern.compile(PHONE_REGEX);
        boolean b = p.matcher(mobile).matches();
        if (!b) {
            return Result.error("手机号不合法");
        }
        if (b) {
            //校验手机号是否注册过
            SelfUserEntity selfUserEntity = selfUserDetailsService.loadUserByMobile(mobile);
            if (null == selfUserEntity) {
                return Result.error("手机号未注册");
            }

            try {
                String code = RandomUtil.randomInt(100000, 999999) + "";
                boolean smsFlag = aliyunSmsUtils.sendInsertUserMsg(mobile, code);
                if (!smsFlag) {
                    return Result.error("验证码发送失败");
                }
                //将验证码存入缓存
                redisCache.setCacheObject(userRedisCode + mobile, code, 60, TimeUnit.SECONDS);
                return Result.success("验证码发送成功");
            } catch (ClientException e) {
                throw new BaseException(1223, e.getErrMsg());
            }
        }
        return Result.error("验证码发送失败");
    }

    @ApiOperation(value = "确认找回密码")
    @PostMapping("/change/password")
    public Result changePassword(HttpServletRequest req,
                                 @RequestParam String mobile,
                                 @RequestParam String newpassword,
                                 @RequestParam String comfirmpassword) {
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("mobile", mobile).eq("status", 1));
        if (!(newpassword).equals(comfirmpassword)) {
            return Result.error("两次密码输入不一致");
        }

        //校验密码格式 密码长度  8~16位，需包含数字、字母、符号中至少2种元素；
        Pattern p = Pattern.compile(PASSWORD_REGEX);
        boolean m = p.matcher(comfirmpassword).matches();
        if (!m) {
            return Result.error("密码格式错误,密码长度8~16位，需包含数字、字母、符号中至少2种元素");
        }

        if(Md5Util.encode(newpassword).equals(sysUser.getPassword())){
            return Result.error("新密码和原密码相同");
        }

        sysUser.setPassword(Md5Util.encode(newpassword));
        boolean b = sysUserService.update(sysUser, new QueryWrapper<SysUser>().eq("mobile", mobile));
        if (b) {
            return Result.success("修改密码成功");
        }
        return Result.error("修改密码失败");
    }

    @RequestLimit(second = 120, maxCount = 5, intervalTime = 60 * 15)
    @ApiOperation("找回密码获取手机验证码")
    @GetMapping("update/password/sms/code")
    public Result achieveMobileCode(HttpServletRequest request, @RequestParam String mobile) {
        boolean getFlag = smsService.getSmsCodeInUpdatePassword(mobile);
        if (getFlag) {
            return Result.success("验证码发送成功");
        }
        return Result.error("验证码发送失败");
    }

    @RequestLimit(second = 120, maxCount = 5, intervalTime = 60 * 15)
    @ApiOperation("注册用户时获取验证码")
    @GetMapping("register/mobile/code")
    public Result getCodeInRegister(@RequestParam String mobile) {
        boolean getFlag = smsService.getCodeInRegister(mobile);
        if (getFlag) {
            return Result.success("验证码发送成功");
        }
        return Result.error("验证码发送失败");
    }

    @ApiOperation(value = "注册新用户")
    @PostMapping("/register/user")
    public Result registerUser(HttpServletRequest req, @RequestBody @Validated RegisterUserDTO registerUserDTO
            //, BindingResult bindingResult
        ) {
//        for(ObjectError error : bindingResult.getAllErr
//        StringBuffer errors = new StringBuffer();ors()){
//            errors.append(",").append(error.getDefaultMessage());
//        }
//
//        if(StringUtils.isNotEmpty(errors)){
//            return Result.error(errors);
//        }

        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username", registerUserDTO.getUserName()).eq("status", 1));
        if (null != sysUser) {
            return Result.error("该用户名已存在,请返回登陆或找回密码.");
        }

        boolean checkFlag = smsService.checkCode(13, registerUserDTO.getMobile(), null, registerUserDTO.getCode());
        if(!checkFlag){
            return Result.error("验证码错误");
        }

        boolean b = sysUserService.save(registerUserDTO.convertUser());
        if (b) {
            redisCache.deleteObject(userRedisCode + TypeEnumUtil.getByCode(TypeEnum.class, 13).getOrderType() + registerUserDTO.getMobile());
            return Result.success("注册新用户成功");
        }
        return Result.error("注册新用户失败");
    }

    @ApiOperation("校验手机验证码(type--11修改密码12修改手机号)")
    @PostMapping("/sms/check/{type}")
    public Result checkCode(HttpServletRequest request, @PathVariable Integer type, @RequestParam String mobile, @RequestParam String code) {
        Long userId = (Long) request.getAttribute("admin");
        boolean checkFlag = smsService.checkCode(type, mobile, userId, code);
        if (checkFlag) {
            redisCache.deleteObject(userRedisCode + TypeEnumUtil.getByCode(TypeEnum.class, type).getOrderType() + mobile);
            return Result.success("验证码校验成功");
        }

        return Result.error("验证码错误");
    }

}