package com.xiaowu.security.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 *
 * @author XiaoWu
 * @date 2019/7/18 10:41
 */
@Configuration
@EnableAuthorizationServer  // 注解就实现认证服务器，实现四种授权模式
public class XiaowuAuthorizationServerConfig {

}
