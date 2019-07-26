package com.xiaowu.test.security;

import com.xiaowu.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author XiaoWu
 * @date 2019/7/26 13:19
 */
@Component
public class TestAuthorizeConfigProvider implements AuthorizeConfigProvider {
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests) {
		authorizeRequests.anyRequest().authenticated();
	}
}
