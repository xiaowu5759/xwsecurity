package com.xiaowu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaowu.entity.User;
import com.xiaowu.security.app.social.AppSignUpUtils;
import com.xiaowu.security.core.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private AppSignUpUtils appSignUpUtils;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 获取当前用户信息
     * @param userDetails
     * @return
     */
    @RequestMapping(value = "/me",method = RequestMethod.GET)
//    public Object getCurrentUser(@AuthenticationPrincipal UserDetails userDetails)
    public Object getCurrentUser(Authentication userDetails)
    {
        return userDetails;
    }

    @RequestMapping(value = "/mes", method = RequestMethod.GET)
    public Object getCurrentUser(Authentication user, HttpServletRequest request) throws UnsupportedEncodingException {
        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "Bearer ");

        Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8"))
                .parseClaimsJws(token).getBody();

        String company = (String)claims.get("company");
        log.info("company-->{}",company);
        return user;


    }


    @RequestMapping(value = "/regist",method = RequestMethod.POST)
    public void regist(User user, HttpServletRequest request){
        // 不管是注册用户，还是绑定用户，都会拿到一个用户唯一标识
        String userId = user.getUsername();
        // 从social中 拿到对应的信息放到userconnection的表中

//        providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));
        appSignUpUtils.doPostSignUp(new ServletWebRequest(request), userId);
    }
}
