package com.xiaowu.security.core.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrowserProperties {

    // 指定默认值
    private  String loginPage = "/xiaowu-signIn.html";

    private String signUpUrl = "/xiaowu-signUp.html";

    // 登录响应的方式,默认是json，枚举类型
    private LoginType loginType = LoginType.JSON;

    // 记住我的时间 一小时
    private int rememberMeSeconds = 3600;

}
