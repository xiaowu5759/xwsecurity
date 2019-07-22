package com.xiaowu.security.app;

import com.xiaowu.security.core.social.XiaowuSpringSocialConfigurer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * spring 容器 bean初始化之前和之后的操作
 * @author XiaoWu
 * @date 2019/7/22 13:41
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {
		return bean;
	}

	/**
	 * 初始化之后， 修改bean中的内容
	 * @param
	 * @param
	 * @return
	 * @throws BeansException
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(StringUtils.equals(beanName, "xiaowuSocialSecurityConfig")){
			XiaowuSpringSocialConfigurer configurer = (XiaowuSpringSocialConfigurer)bean;
			configurer.signupUrl("/social/signUp");
			return configurer;
		}
		return bean;
	}
}
