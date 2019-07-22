package com.xiaowu.security.app;

import com.xiaowu.security.app.jwt.XiaowuJwtTokenEnhancer;
import com.xiaowu.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 配置令牌的存储位置
 * @author XiaoWu
 * @date 2019/7/22 15:19
 */
@Configuration
public class TokenStoreConfig {

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Bean
	@ConditionalOnProperty(prefix = "xiaowu.security.oauth2", name = "storeType", havingValue = "redis")
	public TokenStore redisTokenStore(){
		return new RedisTokenStore(redisConnectionFactory);
	}

	/**
	 * 配置一个静态类
	 * 应为和jwt相关的bean不止一个
	 * 当 配置中 有xiaowu.security.oauth2.storeType = jwt 就会生效
	 * matchIf 如果没有配置，就这个生效
	 */
	@Configuration
	@ConditionalOnProperty(prefix = "xiaowu.security.oauth2", name = "storeType", havingValue = "jwt",matchIfMissing = true)
	public static class JwtTokenConfig{

		@Autowired
		private SecurityProperties securityProperties;

		/**
		 * 关心存取
		 * @return
		 */
		@Bean
		public TokenStore JwtTokenStore(){
			return new JwtTokenStore(jwtAccessTokenConverter());
		}

		/**
		 * 关系生成
		 * 签名
		 * @return
		 */
		@Bean
		public JwtAccessTokenConverter jwtAccessTokenConverter(){
			JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
			accessTokenConverter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
			return accessTokenConverter;
		}

		@Bean
		@ConditionalOnMissingBean(name = "jwtTokenEnhancer")
		public TokenEnhancer jwtTokenEnhancer(){
			return new XiaowuJwtTokenEnhancer();
		}
	}
}
