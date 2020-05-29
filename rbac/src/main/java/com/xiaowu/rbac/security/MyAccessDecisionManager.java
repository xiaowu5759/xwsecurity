package com.xiaowu.rbac.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;

@Component
public class MyAccessDecisionManager implements AccessDecisionManager {
    //decide 方法是判定是否拥有权限的决策方法
    @Override
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {

        System.out.println("Enter self AccessDecisionManager");

        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        String url = null;
        String method = null;
        AntPathRequestMatcher matcher = null;
//        System.out.println(configAttributes);
        for (ConfigAttribute ca : configAttributes) {
            String needRole = ca.getAttribute();
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (ga.getAuthority().equals("ROLE_ANONYMOUS")) {
                    matcher = new AntPathRequestMatcher("/login");
                    if (matcher.matches(request)) {
                        return;
                    }
                }
                // TODO:存在逻辑问题 有用户，但是没方法（这之后需要写成如此）
                else if (ga instanceof MyGrantedAuthority) {
                    MyGrantedAuthority myGrantedAuthority = (MyGrantedAuthority) ga;
                    if (!needRole.equals(myGrantedAuthority.getRole())) {
                        break;
                    }
                    method = myGrantedAuthority.getMethod();
                    if (method.equals(request.getMethod()) || method.equals("ALL")) {
                        System.out.println("method equal");
                        return;
                    }
                }
            }
        }
        throw new AccessDeniedException("no right");
    }


//        for (GrantedAuthority ga : authentication.getAuthorities()){
//            //需要登录
//            if(ga.getAuthority().equals("ROLE_ANONYMOUS")){
//                matcher = new AntPathRequestMatcher("/login");
//                if (matcher.matches(request)) {
//                    return;
//                }
////                    throw new BadCredentialsException("未登录");
//            } else {
//                for (ConfigAttribute ca : configAttributes) {
//                    // 当前请求所需要的权限
//                    String needRole = ca.getAttribute();
//                    System.out.println(needRole);
//                    if (ga instanceof MyGrantedAuthority) {
//                        MyGrantedAuthority myGrantedAuthority = (MyGrantedAuthority) ga;
//
//                        // 判断是否具有访问 角色
//                        if (!needRole.equals(myGrantedAuthority.getRole())) {
////                            break;
//                            // 跳出的圈和我想得不同，双层循环就会出现这种情况
////                            break ca;
//                            break;
//                        }
//                        method = myGrantedAuthority.getMethod();
//                        if (method.equals(request.getMethod()) || method.equals("ALL")) {
//                            System.out.println("method equal");
//                            return;
//                        }
//                    }
//                }
//            }
//        }



//        System.out.println(ite.toString());
////        System.out.println(authentication.getAuthorities().size());
//        for (GrantedAuthority ga : authentication.getAuthorities()) {
//            System.out.println(ga.toString());
//            // authentication size[ROLE_ANONYMOUS]
//            // authentication size[MyGrantedAuthority{url='/index', method='get'}, MyGrantedAuthority{url='/home', method='get'}, MyGrantedAuthority{url='/resource', method='post'}]
//
//            if (ga instanceof MyGrantedAuthority) {
//                MyGrantedAuthority urlGrantedAuthority = (MyGrantedAuthority) ga;
//                url = urlGrantedAuthority.getUrl();
////                System.out.println(url);
//                method = urlGrantedAuthority.getMethod();
////                System.out.println(method);
//                matcher = new AntPathRequestMatcher(url);
//                System.out.println(matcher.toString());
//                if (matcher.matches(request)) {
//                    //当权限表权限的method为ALL时表示拥有此路径的所有请求方式权利。
//                    if (method.equals(request.getMethod()) || "ALL".equals(method)) {
//                        System.out.println("method equal");
//                        return;
//                    }
//                }
//            } else if (ga.getAuthority().equals("ROLE_ANONYMOUS")) {//未登录只允许访问 login 页面
//                matcher = new AntPathRequestMatcher("/login");
//                if (matcher.matches(request)) {
//                    return;
//                }
//            }
//        }
//        throw new AccessDeniedException("no right");



    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }


}
