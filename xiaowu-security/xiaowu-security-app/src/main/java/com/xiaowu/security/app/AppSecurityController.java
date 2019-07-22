package com.xiaowu.security.app;

import com.xiaowu.security.app.social.AppSignUpUtils;
import com.xiaowu.security.core.social.support.SocialUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author XiaoWu
 * @date 2019/7/22 13:47
 */
@RestController
public class AppSecurityController {

	@Autowired
	private ProviderSignInUtils providerSignInUtils;

	@Autowired
	private AppSignUpUtils appSignUpUtils;

	/**
	 * app访问url 引导用户注册绑定
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/social/signUp",method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public SocialUserInfo getSocialUserInfo(HttpServletRequest request){
		SocialUserInfo userInfo = new SocialUserInfo();
		// 从session中拿 connection信息
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
		// 存在key里面的
		userInfo.setProviderId(connection.getKey().getProviderId());
		userInfo.setProviderUserId(connection.getKey().getProviderUserId());
		userInfo.setNickname(connection.getDisplayName());
		userInfo.setHeadimg(connection.getImageUrl());

		// 做一个转存
		appSignUpUtils.saveConnectionData(new ServletWebRequest(request), connection.createData());
		return userInfo;
	}

}
