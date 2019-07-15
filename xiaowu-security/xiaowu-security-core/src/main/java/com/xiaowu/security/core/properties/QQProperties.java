package com.xiaowu.security.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.social.SocialProperties;

@Getter
@Setter
// TODO: 新版本中没有对应依赖
public class QQProperties extends SocialProperties {

    // application id
//    private String appId;

    // application secret
//    private String appSecret;

    /**
     * 第三方id,用来决定发起第三方登录的url,默认是qq
     */
    private String providerId = "qq";
}
