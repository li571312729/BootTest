package com.hefu.auth.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;

@SuppressWarnings("ALL")
public class MobileCodeAuthenticationToken extends AbstractAuthenticationToken {

    private String mobile;
    private String code;

    public MobileCodeAuthenticationToken(String mobile, String code) {
        super(null);
        this.mobile = mobile;
        this.code = code;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
