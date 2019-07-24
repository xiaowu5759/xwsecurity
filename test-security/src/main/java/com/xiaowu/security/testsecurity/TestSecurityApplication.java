package com.xiaowu.security.testsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = {"com.xiaowu"})
@RestController
public class TestSecurityApplication {

	@GetMapping("/me")
	public Authentication me(Authentication user){
		return user;
	}

	public static void main(String[] args) {
		SpringApplication.run(TestSecurityApplication.class, args);
	}

}
