package com.xiaowu.security.core.properties;

/**
 * 安全认证模块的常量接口类，里面包括所有要用到的常量
 * 方便以后维护和扩展
 */
public interface SecurityConstants {

    // 默认的处理验证码
    String DEFAULT_VALIDATE_CODE_URI_PREFIX = "/code";

    // 当请求需要身份认证时，默认跳转的url
    String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";

    // 默认的用户名密码登录请求处理url
    String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/form";

    // 默认的手机验证码登录请求处理url
    String DEFAULT_LOGIN_PROCESSING_URL_MOBILE = "/authentication/mobile";

    // 默认登录页面
    String DEFAULT_LOGIN_PAGE_URL = "/xiaowu-signIn.html";

    // 验证图形验证码时，http请求中默认携带图片验证码信息的参数的名称
    String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";

    // 验证短信验证码时，http请求中默认携带短信验证码信息的参数的名称
    String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";

    /**
     * session失效默认的跳转地址
     */
    String DEFAULT_SESSION_INVALID_URL = "/xiaowu-session-invalid";

    /**
     * 默认的OPENID登录请求处理url
     */
    String DEFAULT_LOGIN_PROCESSING_URL_OPENID = "/authentication/openid";
    /**
     * openid参数名
     */
    String DEFAULT_PARAMETER_NAME_OPENID = "openId";
    /**
     * providerId参数名
     */
    String DEFAULT_PARAMETER_NAME_PROVIDERID = "providerId";

}
