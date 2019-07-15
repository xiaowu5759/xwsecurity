package com.xiaowu.security.browser.support;

import lombok.Data;

@Data
public class SocialUserInfo {

    // 哪个第三方
    private String providerId;

    // 这是openid
    private String providerUserId;

    private String nickname;

    private String headimg;

}
