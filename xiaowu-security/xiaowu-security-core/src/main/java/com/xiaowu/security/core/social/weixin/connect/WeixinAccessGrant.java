package com.xiaowu.security.core.social.weixin.connect;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.oauth2.AccessGrant;

/**
 * 微信的access_token信息与标准的OAuth2协议不同,微信在获取access_token时会同时返回openId,并没有单独的通过accessToken换取openId的服务
 * 所以这里继承了标准AccessGrant,添加了openId字段,作为对微信access_token信息的封装
 */
@Getter
@Setter
public class WeixinAccessGrant extends AccessGrant {

	private String openId;

	public WeixinAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
		super(accessToken, scope, refreshToken, expiresIn);
	}

}
