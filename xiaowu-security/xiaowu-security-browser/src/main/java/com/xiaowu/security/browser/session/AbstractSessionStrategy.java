package com.xiaowu.security.browser.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaowu.security.core.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 抽象的session失效处理器
 */
@Slf4j
// TODO: 不知道是不是策略模式
public class AbstractSessionStrategy {
	/**
	 * 跳转的url
	 */
	private String destinationUrl;

	/**
	 * 重定向策略
	 */
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	/**
	 * 跳转前是否创建新的session
	 */
	private boolean createNewSession = true;

	private ObjectMapper objectMapper = new ObjectMapper();

	public AbstractSessionStrategy(String invalidSessionUrl) {
		Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
		this.destinationUrl = invalidSessionUrl;
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (createNewSession) {
			request.getSession();
		}

		String sourceUrl = request.getRequestURI();
		String targetUrl;

		if (StringUtils.endsWithIgnoreCase(sourceUrl, ".html")) {
			targetUrl = destinationUrl+".html";
			log.info("session失效,跳转到"+targetUrl);
			redirectStrategy.sendRedirect(request, response, targetUrl);
		}else{
			String message = "session已失效";
			if(isConcurrency()){
				message = message + "，有可能是并发登录导致的";
			}
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(message)));
		}

	}

	/**
	 * session失效是否是并发导致的
	 */
	protected boolean isConcurrency() {
		return false;
	}


	public void setCreateNewSession(boolean createNewSession) {
		this.createNewSession = createNewSession;
	}
}
