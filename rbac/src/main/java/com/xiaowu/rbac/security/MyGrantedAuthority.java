package com.xiaowu.rbac.security;

import com.xiaowu.rbac.entity.Role;
import org.springframework.security.core.GrantedAuthority;

public class MyGrantedAuthority implements GrantedAuthority {

    private String url;
    private String method;
    private String role;

    MyGrantedAuthority(String url, String method, String role)
    {
        this.url = url;
        this.method = method;
        this.role = role;
    }

    @Override
    public String getAuthority()
    {
        return this.url + ";" + this.method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "MyGrantedAuthority{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
