package com.xiaowu.security.core.social.weixin.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.nio.charset.Charset;
import java.util.List;

@Slf4j
public class WeixinImpl extends AbstractOAuth2ApiBinding implements Weixin{
    /**
     * 获取用户信息的url
     */
    private static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=";

    /**
     * 工具类,将json串转换为对象
     */
    private ObjectMapper objectMapper;

    public WeixinImpl(String accessToken){
        super(accessToken,TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    /**
     * 默认注册的StringHttpMessageConverter字符集为ISO-8859-1,而微信返回的是UTF-8的
     * @return
     */
    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        messageConverters.remove(0);
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return messageConverters;
    }

    /**
     * 获取微信用户信息
     * @param openId
     * @return
     */
    @Override
    public WeixinUserInfo getUserInfo(String openId) {
        String url = URL_GET_USER_INFO + openId;
        String result = getRestTemplate().getForObject(url, String.class);
        log.info(result);
        if(StringUtils.contains(result, "errcode")){
            return null;
        }
        WeixinUserInfo userInfo = null;
        try{
            userInfo = objectMapper.readValue(result, WeixinUserInfo.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return userInfo;
    }
}
