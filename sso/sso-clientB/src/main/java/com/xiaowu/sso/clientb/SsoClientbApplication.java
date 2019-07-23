package com.xiaowu.sso.clientb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XiaoWu
 * @date 2019/7/23 10:02
 */
@SpringBootApplication
@RestController
// 让sso生效的，要让注解正式的工作，需要一些配置
@EnableOAuth2Sso
public class SsoClientbApplication {
	public static void main(String[] args) {
		SpringApplication.run(SsoClientbApplication.class, args);
	}

	@RequestMapping(value = "/user",method = RequestMethod.GET)
	public Authentication user(Authentication user){
		return user;
	}
}
