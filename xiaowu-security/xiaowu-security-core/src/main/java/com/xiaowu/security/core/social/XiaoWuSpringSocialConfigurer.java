package com.xiaowu.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 自定义 SpringSocialConfigurer中的过滤器
 * 覆盖方法
 */
public class XiaowuSpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    /**
     * 在构造函数中，将值传进来
     * @param filterProcessesUrl
     */
    public XiaowuSpringSocialConfigurer(String filterProcessesUrl){
        this.filterProcessesUrl = filterProcessesUrl;
    }

    /**
     *
     * @param object  // 就是放在filter链上的filter
     * @param <T>
     * @return
     */
    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter)super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        return (T) filter;
    }
}
