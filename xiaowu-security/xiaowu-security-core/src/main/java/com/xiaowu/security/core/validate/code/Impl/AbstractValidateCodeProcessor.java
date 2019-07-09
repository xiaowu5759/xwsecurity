package com.xiaowu.security.core.validate.code.Impl;

import com.xiaowu.security.core.validate.code.ValidateCode;
import com.xiaowu.security.core.validate.code.ValidateCodeGenerator;
import com.xiaowu.security.core.validate.code.ValidateCodeProcessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 抽象的实现类
 * 将整体的逻辑表述完整
 * 不同的地方，用抽象方法，分别实现
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    // 操作session的工具类
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    // 手机系统中所有的{@link ValidateCodeGenerator} 接口的实现
    // 自动注册的map
    // spring启动的时候，会查找所有接口的实现
    // key:就是bean的名字
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGeneratorMap;

    /**
     * 主干逻辑
     * 生成，保存和发送都写在这里了
     * @param request
     * @throws Exception
     */
    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = generate(request);
        save(request,validateCode);
        // 发送是一个抽象方法
        send(request,validateCode);
    }

    /**
     * 保存校验码
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, C validateCode) {
        // 保存code和过期时间
//        ValidateCode code = new ValidateCode(validateCode.getCode(),validateCode.getExpireTime());
        // 保存再session中
        sessionStrategy.setAttribute(request,getSessionKey(request),validateCode);
    }

    private String getSessionKey(ServletWebRequest request) {
        return SESSION_KEY_PREFIX + getProcessorType(request).toString().toUpperCase();
    }

    // spring中常用的方法，叫做依赖查找
    /**
     * 生成验证码
     * @param request
     * @return
     */
    private C generate(ServletWebRequest request) {
        String type = getProcessorType(request);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGeneratorMap.get(type + "CodeGenerator");
        // 调用生成方法
        return (C)validateCodeGenerator.generate(request);

    }

    /**
     * 根据请求的url获取校验码的类型
     * @param request
     * @return
     */
    private String getProcessorType(ServletWebRequest request) {
        return StringUtils.substringAfter(request.getRequest().getRequestURI(),"/code/");
    }

    /**
     * 发送验证码，由子类实现
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;


}
