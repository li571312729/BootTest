package com.hefu.auth.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;

@SuppressWarnings("ALL")
public class UsernameAuthenticationToken extends AbstractAuthenticationToken {

    private String username;
    private String password;

    public UsernameAuthenticationToken(String loginAcct, String password) {
        super(null);
        this.username = loginAcct;
        this.password = password;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}