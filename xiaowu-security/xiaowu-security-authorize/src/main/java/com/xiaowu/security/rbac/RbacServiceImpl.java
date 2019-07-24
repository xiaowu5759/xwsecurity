package com.xiaowu.security.rbac;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * @author XiaoWu
 * @date 2019/7/24 10:52
 */
@Service("rabcService")
public class RbacServiceImpl implements  RbacService{

	private AntPathMatcher antPathMatcher = new AntPathMatcher();



	@Override
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		Object principal = authentication.getPrincipal();
		boolean hasPermission =false;
		if (principal instanceof UserDetails){
			String username = ((UserDetails)principal).getUsername();
			// 读取用户所拥有权限的所有url
			Set<String> urls = new HashSet<>();

			for (String url: urls){
				if(antPathMatcher.match(url, request.getRequestURI())){
					hasPermission = true;
					break;
				}
			}
		}
		return hasPermission;
	}
}
