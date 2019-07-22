package com.xiaowu.security.core.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 设置多个 client
 * @author XiaoWu
 * @date 2019/7/22 14:48
 */
@Getter
@Setter
public class OAuth2Properties {
	// 默认是空数组
	private OAuth2ClientProperties[] clients = {};

	// 签名和验签
	private String jwtSigningKey = "xiaowu";

}
