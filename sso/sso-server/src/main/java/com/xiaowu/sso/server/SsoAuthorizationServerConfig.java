package com.xiaowu.sso.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.AntPathMatcher;

/**
 * @author XiaoWu
 * @date 2019/7/23 9:34
 */
@Configuration
@EnableAuthorizationServer
public class SsoAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

//	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("xiaowu1")
				.secret("xiaowusecret1")
				.redirectUris("http://www.example.com","http://127.0.0.1:8111/client1/login")
				.authorizedGrantTypes("authorization_code","refresh_token")
				.scopes("all")
				.and()
				.withClient("xiaowu2")
				.secret("xiaowusecret2")
				.redirectUris("http://www.example.com","http://127.0.0.1:8222/client2/login")
				.authorizedGrantTypes("authorization_code","refresh_token")
				.scopes("all");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(JwtTokenStore()).accessTokenConverter(jwtAccessTokenConverter());
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// spring security的授权表达式
		// 访问tokenKey需要身份认证
		security.tokenKeyAccess("isAuthenticated()");
	}

	/**
	 * 关于jwt的bean
	 * @return
	 */
	@Bean
	public TokenStore JwtTokenStore(){
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter(){
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("xiaowu");
		return accessTokenConverter;
	}
}
