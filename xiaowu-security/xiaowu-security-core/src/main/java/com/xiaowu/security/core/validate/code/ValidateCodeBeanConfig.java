package com.xiaowu.security.core.validate.code;

import com.xiaowu.security.core.properties.SecurityProperties;
import com.xiaowu.security.core.validate.code.image.ImageCodeGenerator;
import com.xiaowu.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.xiaowu.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 这是一个配置类
 */
@Configuration
public class ValidateCodeBeanConfig {
    @Autowired
    private SecurityProperties securityProperties;

    // 用注解来声明一个spring的bean
    // bean的类型 ValidateCodeGenerator
    // 方法的名字就是最后放到spring容器中bean的名字
    // 单纯这样配置，和@Component 效果是一样的
    // 为了@ConditionalOnMissingBean、
    // 系统启动的时候，初始化这个bean之前，
    // 在容器中不存在imageCodeGenerator bean的时候，才调用

    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator(){
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    // 另外一种写法
    // 在系统中找到接口 的实现，就不会用下面的
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    // 返回的是SmsCodeSender的一个实现
    public SmsCodeSender smsCodeSender(){
        return new DefaultSmsCodeSender();
    }
}
