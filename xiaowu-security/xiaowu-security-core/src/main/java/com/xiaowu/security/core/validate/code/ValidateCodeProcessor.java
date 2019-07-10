package com.xiaowu.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 这是一个接口
 * 验证码的整体逻辑一致，抽象 出来的 模板
 *
 * 校验码处理器，封装不同校验码的处理逻辑
 */
public interface ValidateCodeProcessor {
    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 创建校验码
     * @param request
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * 过滤器，校验验证码
     * @param request
     */
    void validate(ServletWebRequest request);
}
