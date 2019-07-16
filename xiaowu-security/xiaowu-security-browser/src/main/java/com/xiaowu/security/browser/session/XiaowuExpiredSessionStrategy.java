package com.xiaowu.security.browser.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

public class XiaowuExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {
//	@Override
//	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
//		event.getResponse().setContentType("application/json,charset=UTF-8");
//		event.getResponse().getWriter().write("并发登录");
//	}
	public XiaowuExpiredSessionStrategy(String invalidSessionUrl) {
		super(invalidSessionUrl);
	}

	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		onSessionInvalid(event.getRequest(), event.getResponse());
	}

	@Override
	protected boolean isConcurrency() {
		return true;
	}
}
