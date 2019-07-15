package com.xiaowu.security.core.social.qq.config;


import com.xiaowu.security.core.properties.QQProperties;
import com.xiaowu.security.core.properties.SecurityProperties;
import com.xiaowu.security.core.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;

import javax.sql.DataSource;

@Configuration
// 如果系统中，没有配置qq的appid,appSecret,
// 没配的时候，希望不起作用
// 被配置值了，下面的才生效
@ConditionalOnProperty(prefix = "xiaowu.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired(required = false)  //不一定会提供,浏览器情况下就不需要做处理
    private ConnectionSignUp connectionSignUp;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqConfig =securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        repository.setTablePrefix("xiaowu_");
        if(connectionSignUp != null){
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }
}
