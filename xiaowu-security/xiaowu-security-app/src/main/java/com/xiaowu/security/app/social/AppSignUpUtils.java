package com.xiaowu.security.app.social;

import com.xiaowu.security.app.AppSecretException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.TimeUnit;

/**
 * @author XiaoWu
 * @date 2019/7/22 13:15
 */
@Component
public class AppSignUpUtils {

	@Autowired
	private RedisTemplate redisTemplate;

	// 操作数据库的类
	@Autowired
	private UsersConnectionRepository usersConnectionRepository;

	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;

	/**
	 * 将社交用户信息 保存在redis
	 * @param request
	 * @param connectionData
	 */
	public void saveConnectionData(WebRequest request, ConnectionData connectionData){
		redisTemplate.opsForValue().set(getKey(request), connectionData,10, TimeUnit.MINUTES);
	}

	/**
	 * 做绑定
	 * @param request
	 * @param userId
	 */
	public void doPostSignUp(WebRequest request,String userId){
		String key = getKey(request);
		if(!redisTemplate.hasKey(key)){
			throw new AppSecretException("无法找到缓存的用户社交信息");
		}
		ConnectionData connectionData = (ConnectionData)redisTemplate.opsForValue().get(key);
		// 将connectionData转成connection
		Connection<?> connection =  connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId()).createConnection(connectionData);
		usersConnectionRepository.createConnectionRepository(userId).addConnection(connection);
		// 清存储
		redisTemplate.delete(key);
	}

	private String getKey(WebRequest request) {
		String deviceId = request.getHeader("deviceId");
		if (StringUtils.isBlank(deviceId)){
			throw new AppSecretException("设备id参数不能为空");
		}
		return "xiaowu:security:social.connect."+deviceId;
	}
}
