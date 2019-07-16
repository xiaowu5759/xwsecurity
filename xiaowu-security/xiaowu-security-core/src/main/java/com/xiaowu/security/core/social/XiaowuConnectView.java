package com.xiaowu.security.core.social;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class XiaowuConnectView extends AbstractView{

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		httpServletResponse.setContentType("text/html;charset=UTF-8");
		if(model.get("connection") == null){
			httpServletResponse.getWriter().write("<h3>解绑成功<h3>");
		}
		else{
			httpServletResponse.getWriter().write("<h3>绑定成功<h3>");
		}
	}
}
