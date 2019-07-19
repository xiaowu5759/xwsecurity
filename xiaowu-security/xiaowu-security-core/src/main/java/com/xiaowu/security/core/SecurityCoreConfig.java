package com.xiaowu.security.core;


import com.xiaowu.security.core.properties.SecurityProperties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 让配置类生效
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
	@Bean
	public PasswordEncoder passwordEncoder(){
		// 随机生成的盐，混在里面
		return new BCryptPasswordEncoder();
	}
}
