package com.xiaowu.security.browser;

import com.xiaowu.security.browser.authentication.XiaowuAuthenticationFailureHandler;
import com.xiaowu.security.browser.authentication.XiaowuAuthenticationSuccessHandler;
import com.xiaowu.security.core.authentication.AbstractChannelSecurityConfig;
import com.xiaowu.security.core.authentication.mobile.SmsAuthenticaitonSecurityConfig;
import com.xiaowu.security.core.properties.SecurityConstants;
import com.xiaowu.security.core.properties.SecurityProperties;
import com.xiaowu.security.core.validate.code.ValidateCode;
import com.xiaowu.security.core.validate.code.ValidateCodeFilter;

import com.xiaowu.security.core.validate.code.ValidateCodeSecurityConfig;
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

/**
 *
 * 只配置和浏览器登录安全相关的
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

//    @Autowired
//    private AuthenticationFailureHandler xiaowuAuthenticationFailureHandler;
//
//    @Autowired
//    private AuthenticationSuccessHandler xiaowuAuthenticationSuccessHandler;

    // 自动注入 应该注入接口
    @Autowired
//    @Qualifier("myUserDetailService")
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SmsAuthenticaitonSecurityConfig smsAuthenticaitonSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    protected AuthenticationSuccessHandler xiaowuauthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler xiaowuauthenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        // 随机生成的盐，混在里面
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    

    /**
     * 记住我功能的token存取配置
     * @return
     */
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
    protected void configure(HttpSecurity http) throws Exception {
//        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
//        // 设置失败处理器
//        validateCodeFilter.setAuthenticationFailureHandler(xiaowuAuthenticationFailureHandler);
//        validateCodeFilter.setSecurityProperties(securityProperties);
//        validateCodeFilter.afterPropertiesSet();
//
//        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
//        // 设置失败处理器
//        validateCodeFilter.setAuthenticationFailureHandler(xiaowuAuthenticationFailureHandler);
//        validateCodeFilter.setSecurityProperties(securityProperties);
//        validateCodeFilter.afterPropertiesSet();

        // 这是密码登录相关的配置
//        applyPasswordAuthenticationConfig(http);

        // 至少是表单登录
        http.apply(validateCodeSecurityConfig)
                .and()
            // 短信验证相关的配置
            .apply(smsAuthenticaitonSecurityConfig)
                .and()
            .formLogin()
                .loginPage(SecurityConstants.DEFAULT_LOGIN_PAGE_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(xiaowuauthenticationSuccessHandler)
                .failureHandler(xiaowuauthenticationFailureHandler)
                .and()
            // 浏览器特有的配置
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
            // 下面这些 都是授权的配置
            .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URI_PREFIX+"/*").permitAll()
                .anyRequest()
                .authenticated()
                .and()
            .csrf().disable();




//            .formLogin()
//        // basic登录
////        http.httpBasic()
//                // /authentication/require
//                .loginPage("/authentication/require")
////                .loginPage("/authentication/require")
//                // 处理登录的url是什么,form表单提交的地方
//                .loginProcessingUrl("/authentication/form")
//                .failureUrl("/index.html")
//                .defaultSuccessUrl("/index.html").permitAll()
//                .failureHandler(xiaowuAuthenticationFailureHandler)
//                .successHandler(xiaowuAuthenticationSuccessHandler)


    }

}
