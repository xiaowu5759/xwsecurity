package com.xiaowu.security.browser;

import com.xiaowu.security.browser.authentication.XiaowuAuthenticationFailureHandler;
import com.xiaowu.security.browser.authentication.XiaowuAuthenticationSuccessHandler;
import com.xiaowu.security.core.authentication.mobile.SmsAuthenticaitonSecurityConfig;
import com.xiaowu.security.core.properties.SecurityProperties;
import com.xiaowu.security.core.validate.code.SmsCodeFilter;
import com.xiaowu.security.core.validate.code.ValidateCodeFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationFailureHandler xiaowuAuthenticationFailureHandler;

    @Autowired
    private AuthenticationSuccessHandler xiaowuAuthenticationSuccessHandler;

    // 自动注入 应该注入接口
    @Autowired
    @Qualifier("myUserDetailService")
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    // 依赖注入
    @Autowired
    private SmsAuthenticaitonSecurityConfig smsAuthenticaitonSecurityConfig;

    @Bean
    public PasswordEncoder passwordEncoder(){
        // 随机生成的盐，混在里面
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 启动的时候创建表
        // TODO: 再次启动需要注销
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        // 设置失败处理器
        validateCodeFilter.setAuthenticationFailureHandler(xiaowuAuthenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();

        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        // 设置失败处理器
        validateCodeFilter.setAuthenticationFailureHandler(xiaowuAuthenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();

        // 至少是表单登录
        http
                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin()
        // basic登录
//        http.httpBasic()
                // /authentication/require
                .loginPage("/authentication/require")
//                .loginPage("/authentication/require")
                // 处理登录的url是什么,form表单提交的地方
                .loginProcessingUrl("/authentication/form")
                .failureUrl("/index.html")
                .defaultSuccessUrl("/index.html").permitAll()
                .failureHandler(xiaowuAuthenticationFailureHandler)
                .successHandler(xiaowuAuthenticationSuccessHandler)
                .and()
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
                // 下面这些 都是授权的配置
            .authorizeRequests()
                .antMatchers("/authentication/require", securityProperties.getBrowser().getLoginPage(),"/code/*").permitAll()
                .antMatchers("/authentication/form").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .apply(smsAuthenticaitonSecurityConfig);
    }
}
