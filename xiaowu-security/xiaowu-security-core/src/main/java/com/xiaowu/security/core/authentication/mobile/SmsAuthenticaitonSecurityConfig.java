package com.xiaowu.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 短信认证的配置
 * 在其他包里面直接引入
 */
@Configuration
public class SmsAuthenticaitonSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler xiaowuAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler xiaowuAuthenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        super.configure(builder);
        SmsAuthenticationFilter smsAuthenticationFilter = new SmsAuthenticationFilter();
        // 配置authenticationManager
        smsAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        // 配置失败成功处理器
        smsAuthenticationFilter.setAuthenticationSuccessHandler(xiaowuAuthenticationSuccessHandler);
        smsAuthenticationFilter.setAuthenticationFailureHandler(xiaowuAuthenticationFailureHandler);

        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider();
        smsAuthenticationProvider.setUserDetailsService(userDetailsService);

        http.authenticationProvider(smsAuthenticationProvider)
                .addFilterAfter(smsAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
    }
}
