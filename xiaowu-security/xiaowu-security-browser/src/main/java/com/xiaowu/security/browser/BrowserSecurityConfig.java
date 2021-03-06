package com.xiaowu.security.browser;

import com.xiaowu.security.core.authentication.AbstractChannelSecurityConfig;
import com.xiaowu.security.core.authentication.mobile.SmsAuthenticationSecurityConfig;
import com.xiaowu.security.core.authorize.AuthorizeConfigManager;
import com.xiaowu.security.core.properties.SecurityConstants;
import com.xiaowu.security.core.properties.SecurityProperties;

import com.xiaowu.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 *
 * 只配置和浏览器登录安全相关的
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

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
    private SmsAuthenticationSecurityConfig smsAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

//    @Autowired
//    protected AuthenticationSuccessHandler xiaowuauthenticationSuccessHandler;
//
//    @Autowired
//    protected AuthenticationFailureHandler xiaowuauthenticationFailureHandler;

    @Autowired
    private SpringSocialConfigurer xiaowuSocialSecurityConfig;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
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
        applyPasswordAuthenticationConfig(http);

        // 至少是表单登录
        http.apply(validateCodeSecurityConfig)
                .and()
            // 短信验证相关的配置
            .apply(smsAuthenticationSecurityConfig)
                .and()
            // 添加过滤器等相关配置
            .apply(xiaowuSocialSecurityConfig)
                .and()

            // 浏览器特有的配置
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
            // session管理
            .sessionManagement()
//                .invalidSessionUrl(invalidSessionStrategy)
                .invalidSessionStrategy(invalidSessionStrategy)
                .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                // 当session数量达到最大的时候，阻止登录
                .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                .expiredSessionStrategy(sessionInformationExpiredStrategy)
                .and()
                .and()
            .logout()
                .logoutUrl("/signOut")
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSEESIONID")
                .and()
            // 下面这些 都是授权的配置
//            .authorizeRequests()
//                .antMatchers(
//                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
//                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
//                        securityProperties.getBrowser().getSignInUrl(),
//                        securityProperties.getBrowser().getSignUpUrl(),
//                        securityProperties.getBrowser().getSignOutUrl(),
//                        SecurityConstants.DEFAULT_VALIDATE_CODE_URI_PREFIX+"/*",
//                        "/user/regist","/social/user",
//                        "/session/invalid").permitAll()
//                // has_role "ROLE_ADMIN"
//                .antMatchers(HttpMethod.GET,"/user/*").hasRole("ADMIN")
//                .anyRequest()
//                .authenticated()
//                .and()
            .csrf().disable();

        authorizeConfigManager.config(http.authorizeRequests());




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
