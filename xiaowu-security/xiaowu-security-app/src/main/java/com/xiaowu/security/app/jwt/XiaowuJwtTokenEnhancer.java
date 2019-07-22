package com.xiaowu.security.app.jwt;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XiaoWu
 * @date 2019/7/22 16:09
 */
public class XiaowuJwtTokenEnhancer implements TokenEnhancer {
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<>();
		info.put("company", "xiaowu");
		((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
		return accessToken;
	}
}
