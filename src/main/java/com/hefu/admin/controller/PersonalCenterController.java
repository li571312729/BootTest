package com.hefu.admin.controller;

import cn.hutool.core.util.RandomUtil;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hefu.admin.entity.SysUser;
import com.hefu.admin.service.SysUserService;
import com.hefu.auth.security.entity.SelfUserEntity;
import com.hefu.auth.security.service.SelfUserDetailsService;
import com.hefu.common.entity.Result;
import com.hefu.common.exception.BaseException;
import com.hefu.config.api.RequestLimit;
import com.hefu.config.redis.RedisCache;
import com.hefu.sms.service.SmsService;
import com.hefu.sms.utils.AliyunSmsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


/**
 * @author Administrator
 */
@Api(tags = "个人中心模块")
@RestController
@RequestMapping("personal")
@Slf4j
public class PersonalCenterController {

    @Autowired
    AliyunSmsUtils aliyunSmsUtils;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    SelfUserDetailsService selfUserDetailsService;

    @Autowired
    private SmsService smsService;

    @Autowired
    RedisCache redisCache;

    @Value("${user.achievecode}")
    private String userRedisCode;

    public final String PHONE_REGEX = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";


    @ApiOperation(value = "查询当前用户个人信息")
    @GetMapping("/")
    public Result userInfo(HttpServletRequest req) {
        Long userId = (Long) req.getAttribute("admin");
        SysUser one = sysUserService.getOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUserId, userId));
        log.info("查询当前用户个人信息,one={}", one);
        return Result.success(one);
    }

    @RequestLimit(second = 120, maxCount = 5, intervalTime = 60 * 15)
    @ApiOperation("更换手机号获取新手机验证码")
    @GetMapping("new/mobile/code")
    public Result getCodeInChangeMobile(@RequestParam String mobile) {
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
            if (null != selfUserEntity) {
                return Result.error("该手机号已注册");
            }

            try {
                String code = RandomUtil.randomInt(100000, 999999) + "";
                boolean smsFlag = aliyunSmsUtils.sendInsertUserMsg(mobile, code);
                if (!smsFlag) {
                    return Result.error("验证码发送失败");
                }
                //将验证码存入缓存
                redisCache.setCacheObject(userRedisCode + "newPhoneCode:" + mobile, code, 60, TimeUnit.SECONDS);
                return Result.success("验证码发送成功");
            } catch (ClientException e) {
                throw new BaseException(1223, e.getErrMsg());
            }
        }
        return Result.error("验证码发送失败");
    }

    @RequestLimit(second = 120, maxCount = 5, intervalTime = 60 * 15)
    @ApiOperation("更换手机号获取验证码")
    @GetMapping("change/mobile/code")
    public Result getCodeInNewMobile(@RequestParam String mobile) {
        boolean getFlag = smsService.getCodeInChangeMobile(mobile);
        if (getFlag) {
            return Result.success("验证码发送成功");
        }
        return Result.error("验证码发送失败");
    }

    @ApiOperation("修改手机号")
    @PutMapping("change/mobile")
    public Result changeMobile(HttpServletRequest req, @RequestParam String mobile, @RequestParam String code) {
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
            Long userId = (Long) req.getAttribute("admin");
            //校验手机号是否注册过
            SelfUserEntity selfUserEntity = selfUserDetailsService.loadUserByMobile(mobile);
            if (null != selfUserEntity) {
                return Result.error("该手机号已注册");
            }

            String newPhoneCode = redisCache.getCacheObject(userRedisCode + "newPhoneCode:" + mobile);
            if (!StringUtils.isNotBlank(newPhoneCode)) {
                return Result.error("请重新获取手机验证码");
            }

            if (newPhoneCode.equals(code)) {
                SysUser s = new SysUser();
                s.setUserId(userId);
                s.setMobile(mobile);
                s.setUpdateTime(new Date());
                boolean b1 = sysUserService.updateById(s);
                if (b1) {
                    return Result.success("修改手机号成功");
                }
            } else {
                return Result.error("验证码错误");
            }
        }
        return Result.error("修改手机号失败");
    }
}