package com.xiaowu.security.browser.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaowu.security.core.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
//@Component
public class XiaowuLogoutSuccessHandler implements LogoutSuccessHandler {

	public XiaowuLogoutSuccessHandler(String signOutUrl){
		this.signOutUrl = signOutUrl;
	}

//	@Autowired
	private String signOutUrl;

	private ObjectMapper objectMapper = new ObjectMapper();

	// TODO: 如果用户配置了页面就返回页面，否则返回json
	@Override
	public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
		log.info("退出成功");
		if(StringUtils.isBlank(signOutUrl)){
			httpServletResponse.setContentType("application/json;charset=UTF-8");
			httpServletResponse.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功")));
		}else{
			httpServletResponse.sendRedirect(signOutUrl);

		}

	}
}
