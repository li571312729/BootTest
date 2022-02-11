package com.hefu.auth.config;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("ALL")
public class UsernameAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final String usernameParameter = "mobile";
    private final String passwordParameter = "password";
    private boolean postOnly = true;

    public UsernameAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/user/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        // 用户名称
        String mobile = obtainUsername(request);
        if (StringUtils.isEmpty(mobile)) {
            throw new AuthenticationServiceException("手机号不能为空");
        }
        // 密码
        String password = obtainPassword(request);
        if (StringUtils.isEmpty(password)) {
            throw new AuthenticationServiceException("密码不能为空");
        }

        return this.getAuthenticationManager().authenticate(new UsernameAuthenticationToken(mobile, password));
    }


    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }

    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }

}
