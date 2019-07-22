package com.xiaowu.security.app;

import com.xiaowu.security.core.properties.OAuth2ClientProperties;
import com.xiaowu.security.core.properties.SecurityProperties;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author XiaoWu
 * @date 2019/7/18 10:41
 */
@Configuration
@EnableAuthorizationServer  // 注解就实现认证服务器，实现四种授权模式
public class XiaowuAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private TokenStore tokenStore;

	/**
	 * 只有在jwt情况下才生效的
	 * 正常情况下 是没有这个类的
	 */
	@Autowired(required = false)
	private JwtAccessTokenConverter jwtAccessTokenConverter;

	@Autowired(required = false)
	private TokenEnhancer jwtTokenEnhancer;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService)
		.tokenStore(tokenStore);
		if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null){
			TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
			List<TokenEnhancer> enhancers = new ArrayList<>();
			enhancers.add(jwtTokenEnhancer);
			enhancers.add(jwtAccessTokenConverter);
			enhancerChain.setTokenEnhancers(enhancers);

			endpoints
					//用enhancerChain来配置endpoints这个端点
					.tokenEnhancer(enhancerChain)
					.accessTokenConverter(jwtAccessTokenConverter);
		}
	}

	/**
	 * 遍历设置
	 * @param clients
	 * @throws Exception
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// clientId clientSecret 就失效了
		// jdbc和memory 两种获取方式
//		clients.inMemory().withClient("xiaowu")
//				.secret("xiaowusecret")
//				.accessTokenValiditySeconds(7200)
//				.authorizedGrantTypes("refresh_token","password")
//				.scopes("all","read","write");
		// 如果设置不好，就会出现refresh_token没有的问题
		InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
		if(ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())){
			for (OAuth2ClientProperties client : securityProperties.getOauth2().getClients()){
				builder.withClient(client.getClientId())
						.secret(client.getClientSecret())
						.authorizedGrantTypes("refresh_token","password")
						.accessTokenValiditySeconds(client.getAccessTokenValiditySeconds())
						.refreshTokenValiditySeconds(2592000)
//						.scopes("all")
						;
			}
		}

	}
}
