package com.xiaowu.security.core.social.weixin.connect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Slf4j
public class WeixinOAuth2Template extends OAuth2Template{

	private String clientId;

	private String clientSecret;

	private String accessTokenUrl;

	private static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

	public WeixinOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		setUseParametersForClientAuthentication(true);
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.accessTokenUrl = accessTokenUrl;
	}

	/**
	 * 与OAuth默认参数不同
	 * @param authorizationCode
	 * @param redirectUri
	 * @param additionalParameters
	 * @return
	 */
//	if (useParametersForClientAuthentication) {
//		params.set("client_id", clientId);
//		params.set("client_secret", clientSecret);
//	}
//		params.set("code", authorizationCode);
//		params.set("redirect_uri", redirectUri);
//		params.set("grant_type", "authorization_code");
//		if (additionalParameters != null) {
//		params.putAll(additionalParameters);
//	}
	@Override
	public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri,
	                                     MultiValueMap<String, String> additionalParameters) {
		StringBuilder accessTokenRequestUrl = new StringBuilder(accessTokenUrl);

		accessTokenRequestUrl.append("?appid="+clientId);
		accessTokenRequestUrl.append("&secret="+clientSecret);
		accessTokenRequestUrl.append("&code="+authorizationCode);
		accessTokenRequestUrl.append("&grant_type=authorization_code");
		accessTokenRequestUrl.append("&redirect_uri="+redirectUri);

		return getAccessToken(accessTokenRequestUrl);
	}

	/**
	 * 获取访问令牌，返回访问权限，微信定义的正确的返回
	 * @param accessTokenRequestUrl
	 * @return
	 */
	private AccessGrant getAccessToken(StringBuilder accessTokenRequestUrl) {
		log.info("获取access_token, 请求URL: "+accessTokenRequestUrl.toString());
		String response = getRestTemplate().getForObject(accessTokenRequestUrl.toString(), String.class);
		log.info("获取access_token,响应内容："+response);
		Map<String, Object> result = null;
		try {
			//将返回的json串转换成map
			result = new ObjectMapper().readValue(response, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//返回错误码时直接返回空
		if(StringUtils.isNotBlank(MapUtils.getString(result, "errcode"))){
			String errcode = MapUtils.getString(result, "errcode");
			String errmsg = MapUtils.getString(result, "errmsg");
			throw new RuntimeException("获取access token失败, errcode:"+errcode+", errmsg:"+errmsg);
		}

		WeixinAccessGrant accessToken = new WeixinAccessGrant(
				MapUtils.getString(result, "access_token"),
				MapUtils.getString(result, "scope"),
				MapUtils.getString(result, "refresh_token"),
				MapUtils.getLong(result, "expires_in"));
		// 封装
		accessToken.setOpenId(MapUtils.getString(result, "openid"));
		return accessToken;
	}
}
