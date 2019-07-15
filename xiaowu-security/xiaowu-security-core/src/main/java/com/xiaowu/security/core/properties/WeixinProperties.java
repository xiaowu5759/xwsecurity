package com.xiaowu.security.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.social.SocialProperties;

@Getter
@Setter
public class WeixinProperties extends SocialProperties {

    // 第三方id，用来决定发起第三方登录的url,默认是weixin
    private String providerId = "weixin";
}
