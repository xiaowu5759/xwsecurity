package com.xiaowu.security.core.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrowserProperties {

    // 指定默认值
    private  String signInUrl = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    // 默认的注册页
    private String signUpUrl = "/xiaowu-signUp.html";

    // 这个属性默认设置为空
    private String signOutUrl ;

    // 登录响应的方式,默认是json，枚举类型
    private LoginType loginType = LoginType.JSON;

    /**
     * 登录成功后跳转的地址，如果设置了此属性，则登录成功后总是会跳到这个地址上。
     * 只在signInResponseType为REDIRECT时生效
     */
    private String signInSuccessUrl;

    // 记住我的时间 一小时
    private int rememberMeSeconds = 604800;

    // session配置项
    private SessionProperties session = new SessionProperties();

}
