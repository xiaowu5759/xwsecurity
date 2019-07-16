package com.xiaowu.security.core.social.weixin.config;

import com.xiaowu.security.core.properties.SecurityProperties;
import com.xiaowu.security.core.properties.WeixinProperties;
import com.xiaowu.security.core.social.XiaowuConnectView;
import com.xiaowu.security.core.social.weixin.connect.WeixinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.web.servlet.View;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(prefix = "xiaowu.security.social.weixin",name = "app-id")
@Order(2)
/**
 * 继承某种自动配置
 */
public class WeixinAutoConfig extends SocialAutoConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		WeixinProperties weixinProperties = securityProperties.getSocial().getWeixin();
		return new WeixinConnectionFactory(weixinProperties.getProviderId(), weixinProperties.getAppId(), weixinProperties.getAppSecret());
	}

	@Bean({"connect/weixinConnected", "connect/weixinConnect"})
	@ConditionalOnMissingBean(name = "weixinConnectedView")
	public View weixinConnectView(){
		return new XiaowuConnectView();
	}

}
