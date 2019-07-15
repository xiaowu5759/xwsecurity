package com.xiaowu.security.core.social.qq.connect;

import com.xiaowu.security.core.social.qq.api.QQ;
import com.xiaowu.security.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * 和OAuth2AuthenticationService<S>
 *     不是一个
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;

    // 获取Authorization Code
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";

    // 通过Authorization Code获取Access Token
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";


    /**
     * 创建service provider
     * @param appId
     * @param appSecret
     */
    public QQServiceProvider(String appId, String appSecret ) {
        // OAuth2Template，用授权码交换Token
        // 期待返回的 ContentType 应该是json
        super(new QQOAuth2Template(appId,appSecret,URL_AUTHORIZE,URL_ACCESS_TOKEN));
        // 将传入的appId,设置到本地变量中
        this.appId = appId;
    }

    /**
     * 获取接口的实现
     * @param accessToken
     * @return
     */
    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }
}
