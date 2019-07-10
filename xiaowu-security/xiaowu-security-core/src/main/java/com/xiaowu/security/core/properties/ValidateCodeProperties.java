package com.xiaowu.security.core.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 在图像验证码上面再封一层
 * 兼容短信验证码
 */
@Getter
@Setter
public class ValidateCodeProperties {
    private ImageCodeProperties image = new ImageCodeProperties();

    private SmsCodeProperties sms = new SmsCodeProperties();
}
