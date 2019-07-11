package com.xiaowu.security.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.social.SocialProperties;

@Getter
@Setter
public class QQProperties extends SocialProperties {

    // application id
//    private String appId;

    // application secret
//    private String appSecret;

    private String providerId = "qq";
}
