package com.xiaowu.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private static final String URL_GET_OPENIN = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    private static final String URL_GET_USERINGO = "https://graph.qq.com/user/get_user_info?" +
            "oauth_consumer_key=%s&" +
            "openid=%s";

    // 申请QQ登录成功后，分配给应用的appid
    private String appId;

    // 用户的ID，与QQ号码一一对应
    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accesToken,String appId){
        super(accesToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;

        String url = String.format(URL_GET_OPENIN,accesToken);
        // 调用父类函数，获取openId
        String result = getRestTemplate().getForObject(url,String.class);
        log.info(result);
        this.openId = StringUtils.substringBetween(result,",\"openid\":\"","\"}");
    }

    @Override
    public QQUserInfo getUserInfo(){

        String url = String.format(URL_GET_USERINGO,appId,openId);
        String result = getRestTemplate().getForObject(url,String.class);
        log.info(result);
        QQUserInfo qqUserInfo;
        // 将openId 放到userInfo中
        try {
            qqUserInfo = objectMapper.readValue(result, QQUserInfo.class);
            qqUserInfo.setOpenId(openId);
            return qqUserInfo;
        } catch (Exception e) {
            // 包装成运行时异常
            throw new RuntimeException("获取用户信息失败");
        }
    }
}
