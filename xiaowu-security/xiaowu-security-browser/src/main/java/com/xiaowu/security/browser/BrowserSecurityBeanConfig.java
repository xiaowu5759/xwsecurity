package com.xiaowu.security.browser;

import com.xiaowu.security.browser.logout.XiaowuLogoutSuccessHandler;
import com.xiaowu.security.browser.session.XiaowuExpiredSessionStrategy;
import com.xiaowu.security.browser.session.XiaowuInvalidSessionStrategy;
import com.xiaowu.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

@Configuration
public class BrowserSecurityBeanConfig {

	@Autowired
	private SecurityProperties securityProperties;

	/**
	 * session失效时的处理策略配置
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(InvalidSessionStrategy.class)
	public InvalidSessionStrategy invalidSessionStrategy(){
		return new XiaowuInvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
	}

	/**
	 * 并发登录导致前一个session失效时的处理策略配置
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
	public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
		return new XiaowuExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
	}

	/**
	 * 退出时的处理策略配置
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(LogoutSuccessHandler.class)
	public LogoutSuccessHandler logoutSuccessHandler(){
		return new XiaowuLogoutSuccessHandler(securityProperties.getBrowser().getSignOutUrl());
	}
}
