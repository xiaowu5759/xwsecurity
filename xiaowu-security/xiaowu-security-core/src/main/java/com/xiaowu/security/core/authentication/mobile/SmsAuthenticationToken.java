package com.xiaowu.security.core.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SmsAuthenticationToken extends AbstractAuthenticationToken {

    // 存放认证信息的，登录之前放手机号，登录之后放登录成功的用户
    private final Object principal;
    // 这里是密码 ？
//    private Object credentials;

    public SmsAuthenticationToken(String mobile) {
        super((Collection) null);
        this.principal = mobile;
//        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    public SmsAuthenticationToken(Object principal,  Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
//        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    // 实现父类的抽象方法
    public Object getCredentials() {
//        return this.credentials;
        return null;
    }


    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
//        this.credentials = null;
    }
}
