package com.xiaowu.security.core.authorize;

import com.xiaowu.security.core.properties.SecurityConstants;
import com.xiaowu.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author XiaoWu
 * @date 2019/7/24 9:07
 */
@Component
@Order(Integer.MIN_VALUE)
public class XiaowuAuthorizeConfigProvider implements AuthorizeConfigProvider {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		config.antMatchers(
				SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
				SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
				SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_OPENID,
				securityProperties.getBrowser().getLoginPage(),
				securityProperties.getBrowser().getSignUpUrl(),
				securityProperties.getBrowser().getSignOutUrl(),
				SecurityConstants.DEFAULT_VALIDATE_CODE_URI_PREFIX+"/*",
				securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
				"/user/regist","/social/user"
				).permitAll();
	}
}
