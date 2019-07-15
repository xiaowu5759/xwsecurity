package com.xiaowu.security.core.social.weixin.api;

/**
 * 面向接口编程，获取weixin用户信息
 */
public interface Weixin {

    /**
     * 需要一个参数，标准实现
     * @param openId
     * @return
     */
    WeixinUserInfo getUserInfo(String openId);
}
