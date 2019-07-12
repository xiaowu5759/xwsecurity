package com.xiaowu.security.core.social.qq.config;


import com.xiaowu.security.core.properties.QQProperties;
import com.xiaowu.security.core.properties.SecurityProperties;
import com.xiaowu.security.core.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

@Configuration
// 如果系统中，没有配置qq的appid,appSecret,
// 没配的时候，希望不起作用
// 被配置值了，下面的才生效
@ConditionalOnProperty(prefix = "xiaowu.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqConfig =securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
    }
}
