package com.hefu.auth.security.handler;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hefu.admin.entity.SysUser;
import com.hefu.admin.service.SysUserService;
import com.hefu.auth.security.entity.SelfUserEntity;
import com.hefu.auth.util.JWTTokenUtil;
import com.hefu.auth.util.ResultUtil;
import com.hefu.config.redis.RedisCache;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 登录成功处理类
 */
@Slf4j
@Component
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    RedisCache redisCache;
    @Autowired
    private SysUserService sysUserService;
    @Value("${token.key}")
    private String tokenKey;
    @Value("${token.key.date}")
    private Integer tokenKeyDate;

    /**
     * 登录成功返回结果
     */
    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 组装JWT
        SelfUserEntity selfUserEntity = (SelfUserEntity) authentication.getPrincipal();
        SysUser one = sysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserId, selfUserEntity.getUserId())
                .eq(SysUser::getStatus, 1).last("limit 1"));
        String token = JWTTokenUtil.createAccessToken(selfUserEntity, tokenKeyDate);
        String key = tokenKey + IdUtil.simpleUUID();
        redisCache.setCacheObject(key, token, tokenKeyDate , TimeUnit.DAYS);
        // 封装返回参数
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("code", 200);
        resultData.put("message", "登录成功");
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("token", key);
        map.put("phone", one.getMobile());
        map.put("name", one.getName());
        resultData.put("data", map);
        ResultUtil.responseJson(response, resultData);
    }
}