package com.hefu.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mengkai
 * @Description: 验证拦截
 */
@Component
@Log
public class LoginStatusFilter implements HandlerInterceptor {

    /**
     * 请求进去controller进行请求拦截
     * 验证是否登录
     *
     * @param request  req
     * @param response resp
     * @param handler  handler
     * @return boolean
     * @throws Exception exception
     * @Description: 请求登陆验证是否处于登录状态
     * @since 1.0
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String tokenHeader = request.getHeader("hefu");
        if (null != tokenHeader) {
            Claims claims = null;
            // 解析JWT
            claims = Jwts.parser()
                    .setSigningKey("hefu")
                    .parseClaimsJws(tokenHeader)
                    .getBody();
            // 获取用户名
            String username = claims.getSubject();
            String id = claims.getId() + "";
            if (org.apache.commons.lang3.StringUtils.isNotBlank(username) && org.apache.commons.lang3.StringUtils.isNotBlank(id)) {
                return true;
            }
        }
        return false;
    }
}