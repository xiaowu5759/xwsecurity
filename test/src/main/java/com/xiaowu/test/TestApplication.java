package com.xiaowu.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XiaoWu
 * @date 2019/7/26 12:24
 */
@SpringBootApplication(scanBasePackages = {"com.xiaowu"})
@RestController
public class TestApplication {
	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class,args);
	}

	@RequestMapping(value = "/me",method = RequestMethod.GET)
//    public Object getCurrentUser(@AuthenticationPrincipal UserDetails userDetails)
	public Object getCurrentUser(Authentication userDetails)
	{
		return userDetails;
	}
}
