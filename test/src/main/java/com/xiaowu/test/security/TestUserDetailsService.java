package com.xiaowu.test.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author XiaoWu
 * @date 2019/7/26 12:44
 */
@Service
public class TestUserDetailsService implements UserDetailsService, SocialUserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new User(username, passwordEncoder.encode("123456"), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
	}

	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		return new SocialUser(userId,passwordEncoder.encode("123456"),
				AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER  "));
	}
}
