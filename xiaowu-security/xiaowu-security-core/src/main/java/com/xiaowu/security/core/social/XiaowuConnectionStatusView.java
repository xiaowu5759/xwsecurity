package com.xiaowu.security.core.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * bean的名字 就是controller的地址
 */
@Component("connect/status")
public class XiaowuConnectionStatusView extends AbstractView {

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * render方法，渲染最后的视图
	 * @param model
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @throws Exception
	 */
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>)model.get("connectionMap");
		// weixin qq weibo 是否绑定
		Map<String, Boolean> result = new HashMap<>();
		for(String key : connections.keySet()){
			result.put(key, CollectionUtils.isNotEmpty(connections.get(key)));
		}
		httpServletResponse.setContentType("application/json;charset=UTF-8");
		httpServletResponse.getWriter().write(objectMapper.writeValueAsString(result));
	}
}
