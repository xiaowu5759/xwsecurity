package com.xiaowu.security.core.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageCodeProperties extends SmsCodeProperties{
    // 设置构造函数，ImageCode默认是4，SmsCode默认长度是6
    public ImageCodeProperties() {
        setLength(4);
    }

    // 有关验证码的基本参数
    private int width = 67;
    private int height = 23;
//    private int length = 4;
//    private int expireIn = 60;  // 过期时间 60秒

    // 验证码拦截的接口可配置
//    private String url;


}
