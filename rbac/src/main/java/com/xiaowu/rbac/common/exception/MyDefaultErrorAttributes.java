package com.xiaowu.rbac.common.exception;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;


//public class MyDefaultErrorAttributes extends DefaultErrorAttributes {
//
//    /**
//     * 重写error页面获取信息的方法
//     * @param webRequest
//     * @param includeStackTrace
//     * @return
//     */
//    @Override
//    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
//        Map<String,Object> map = new HashMap<>();
//        // request 域中的值是从exception中设置
//        // 从 request 域中获取 code
//        Object code = webRequest.getAttribute("code", RequestAttributes.SCOPE_REQUEST);
//        // 从 request 域中获取 message
//        Object message = webRequest.getAttribute("message", RequestAttributes.SCOPE_REQUEST);
//
//        map.put("code", code);
//        map.put("msg", message);
//        return map;
//    }
//
//}
