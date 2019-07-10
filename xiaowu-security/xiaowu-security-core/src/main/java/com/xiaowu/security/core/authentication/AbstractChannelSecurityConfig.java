package com.xiaowu.security.core.authentication;

import com.xiaowu.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected AuthenticationSuccessHandler xiaowuauthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler xiaowuauthenticationFailureHandler;

    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception{
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_LOGIN_PAGE_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(xiaowuauthenticationSuccessHandler)
                .failureHandler(xiaowuauthenticationFailureHandler);
    }

}
