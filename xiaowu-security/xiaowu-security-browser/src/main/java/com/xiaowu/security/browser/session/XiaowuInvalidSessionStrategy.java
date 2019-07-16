package com.xiaowu.security.browser.session;

import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认的session失效处理器
 */
public class XiaowuInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {
	public XiaowuInvalidSessionStrategy(String invalidSessionUrl) {
		super(invalidSessionUrl);
	}

	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
		onSessionInvalid(request, response);
	}
}
