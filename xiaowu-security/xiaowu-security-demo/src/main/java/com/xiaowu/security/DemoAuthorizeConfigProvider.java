package com.xiaowu.security;

import com.xiaowu.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author XiaoWu
 * @date 2019/7/24 9:54
 */
@Component
@Order(Integer.MAX_VALUE)
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {
	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		// 任何请求都需要 这样的规则
//		config.anyRequest().access("@rabcService.hasPermission(request,authentication)");
		config.antMatchers("/user").authenticated();
		return true;
	}
}
