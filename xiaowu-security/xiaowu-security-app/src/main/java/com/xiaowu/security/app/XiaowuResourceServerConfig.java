package com.xiaowu.security.app;

import com.xiaowu.security.core.authentication.mobile.SmsAuthenticaitonSecurityConfig;
import com.xiaowu.security.core.properties.SecurityConstants;
import com.xiaowu.security.core.properties.SecurityProperties;
import com.xiaowu.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 用于开启oauth的资源服务器的配置
 * @author XiaoWu
 * @date 2019/7/18 13:29
 */
@Configuration
@EnableResourceServer
public class XiaowuResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	protected AuthenticationSuccessHandler xiaowuauthenticationSuccessHandler;

	@Autowired
	protected AuthenticationFailureHandler xiaowuauthenticationFailureHandler;

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private SmsAuthenticaitonSecurityConfig smsAuthenticaitonSecurityConfig;

	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;

	@Autowired
	private SpringSocialConfigurer xiaowuSocialSecurityConfig;


	@Override
	public void configure(HttpSecurity http) throws Exception {
		// 这是密码登录相关的配置
		http.formLogin()
				.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
				.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
				.successHandler(xiaowuauthenticationSuccessHandler)
				.failureHandler(xiaowuauthenticationFailureHandler);

		// 至少是表单登录
		http
				.apply(validateCodeSecurityConfig)
				.and()
				// 短信验证相关的配置
				.apply(smsAuthenticaitonSecurityConfig)
				.and()
				// 添加过滤器等相关配置
				.apply(xiaowuSocialSecurityConfig)
				.and()
			// 下面这些 都是授权的配置
			.authorizeRequests()
				.antMatchers(
						SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
						SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
						securityProperties.getBrowser().getLoginPage(),
						securityProperties.getBrowser().getSignUpUrl(),
						securityProperties.getBrowser().getSignOutUrl(),
						SecurityConstants.DEFAULT_VALIDATE_CODE_URI_PREFIX+"/*",
						"/user/regist","/social/user",
						"/session/invalid").permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.csrf().disable();
	}
}
