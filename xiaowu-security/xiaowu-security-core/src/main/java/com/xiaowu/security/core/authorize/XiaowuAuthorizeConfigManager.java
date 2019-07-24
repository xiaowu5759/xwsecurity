package com.xiaowu.security.core.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author XiaoWu
 * @date 2019/7/24 9:23
 */
@Component
public class XiaowuAuthorizeConfigManager implements AuthorizeConfigManager {

	@Autowired
	private List<AuthorizeConfigProvider> authorizeConfigProviders;

	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		for(AuthorizeConfigProvider authorizeConfigProvider :authorizeConfigProviders){
			authorizeConfigProvider.config(config);
		}
		// 会覆盖权限控制 代码
//		config.anyRequest().authenticated();
	}
}
