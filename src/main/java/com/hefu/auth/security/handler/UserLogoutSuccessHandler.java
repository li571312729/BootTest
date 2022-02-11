package com.hefu.auth.security.handler;

import com.hefu.auth.util.ResultUtil;
import com.hefu.common.utils.Utils;
import com.hefu.config.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 登出成功处理类
 */
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {

    private static RedisCache redisCache;

    @Autowired
    public void setRedisCache(RedisCache redisCache) {
        UserLogoutSuccessHandler.redisCache = redisCache;
    }

    /**
     * 用户登出返回结果
     * 这里应该让前端清除掉Token
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Map<String, Object> resultData = new HashMap<>(16);
        String tokenHeader;
        if (Utils.notEmpty(request.getHeader("hefu"))) {
            tokenHeader = request.getHeader("hefu");
        } else {
            tokenHeader = request.getParameter("hefu");
        }

        // 清除token
        if (Utils.notEmpty(tokenHeader)) {
            redisCache.deleteObject(tokenHeader);
        }

        resultData.put("code", "200");
        resultData.put("msg", "登出成功");
        SecurityContextHolder.clearContext();
        ResultUtil.responseJson(response, resultData);
    }
}