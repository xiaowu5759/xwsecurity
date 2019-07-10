package com.xiaowu.security.core.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QQProperties {

    // application id
    private String appId;

    // application secret
    private String appSecret;

    private String providerId = "qq";
}
