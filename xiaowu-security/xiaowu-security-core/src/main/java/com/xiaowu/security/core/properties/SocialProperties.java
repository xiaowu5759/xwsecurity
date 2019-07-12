package com.xiaowu.security.core.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialProperties {

    private String filterProcessesUrl = "/auth";

    // 设置初始化的值
    private QQProperties qq = new QQProperties();

}
