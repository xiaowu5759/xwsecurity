package com.xiaowu.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @author XiaoWu
 * @date 2019/7/22 9:50
 */
public interface SocialAuthenticationFilterPostProcessor {

	void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
