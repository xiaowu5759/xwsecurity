package com.xiaowu.security.testsecurity.security;

import com.xiaowu.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author XiaoWu
 * @date 2019/7/25 14:29
 */
@Component
public class TestAuthorizeConfigProvider implements AuthorizeConfigProvider {
	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests) {
		authorizeRequests.anyRequest().authenticated();
	}
}
