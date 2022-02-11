package com.hefu.auth.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("ALL")
public class MobileCodeAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final String usernameParameter = "mobile";
    private final String passwordParameter = "code";
    private boolean postOnly = true;

    public MobileCodeAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/user/login/mobile", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        if (postOnly && !"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        // 电话号码
        String mobile = obtainUsername(request);
        if (StringUtils.isBlank(mobile)) {
            throw new AuthenticationServiceException("电话号码不能为空");
        }
        // 验证码
        String code = obtainPassword(request);
        if (StringUtils.isBlank(code)) {
            throw new AuthenticationServiceException("验证码不能为空");
        }

        return this.getAuthenticationManager().authenticate(new MobileCodeAuthenticationToken(mobile, code));
    }


    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }

    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }



}
