package com.xiaowu.controller;

import com.xiaowu.entity.User;
import com.xiaowu.security.app.social.AppSignUpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private AppSignUpUtils appSignUpUtils;

    /**
     * 获取当前用户信息
     * @param userDetails
     * @return
     */
    @RequestMapping(value = "/me",method = RequestMethod.GET)
    public Object getCurrentUser(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails;
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
