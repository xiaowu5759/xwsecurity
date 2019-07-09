package com.xiaowu.security.core.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsCodeProperties {

    private int length = 6;

    private int expireIn = 60;

    private String url;

}
