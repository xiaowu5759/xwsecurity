package com.xiaowu.security.core.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author XiaoWu
 * @date 2019/7/22 15:03
 */
@Getter
@Setter
public class OAuth2ClientProperties {

	private String clientId;

	private String clientSecret;

	private int accessTokenValiditySeconds = 7200;
}
