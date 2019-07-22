package com.xiaowu.security.browser;


import com.xiaowu.security.core.support.SimpleResponse;
import com.xiaowu.security.core.social.support.SocialUserInfo;
import com.xiaowu.security.core.properties.SecurityConstants;
import com.xiaowu.security.core.properties.SecurityProperties;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
public class BrowserSecurityController {
    // 从请求缓存中拿
    private RequestCache requestCache = new HttpSessionRequestCache();

    // 跳转 security的一个工具
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /**
     * 当需要身份认证时，跳转到这里
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = SecurityConstants.DEFAULT_UNAUTHENTICATION_URL, method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)  // 返回的状态码 return 401
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(request,response);

        if (savedRequest != null){
            // 请求跳转的url
            String targetUrl = savedRequest.getRedirectUrl();
            log.info("引发跳转的请求是："+targetUrl);
            if (StringUtils.endsWithIgnoreCase(targetUrl,".html")){
                // 做跳转
                // 不是永远只传到 标准的登录页
//                log.info(securityProperties.getBrowser().getLoginPage());
                redirectStrategy.sendRedirect(request,response,securityProperties.getBrowser().getLoginPage());
//                跳转走了，不继续执行
             }
        }

        return new SimpleResponse("访问的服务需要身份验证");
    }

//    @RequestMapping("/authentication/form")
//    public void login(){
//        log.info("hello");
//    }

    @RequestMapping(value = "/social/user")
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request){
        SocialUserInfo userInfo = new SocialUserInfo();
        // 从session中拿 connection信息
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        // 存在key里面的
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        userInfo.setHeadimg(connection.getImageUrl());
        return userInfo;
    }

    @RequestMapping(value = "/session/invalid",method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)  // 返回的状态码 return 401
    public SimpleResponse sessionInvalid(){
        String message = "session失效";
        return new SimpleResponse(message);
    }
}
