package com.xiaowu.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 校验码处理器管理器，要获取先来这里通过验证和查询
 */
@Component
public class ValidateCodeProcessorHolder {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessorMap;

    public ValidateCodeProcessor findValidateCodeProcessor(String type){
        // 先获取bean名字
        String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        // 在map中 获取接口实现方式
        ValidateCodeProcessor processor = validateCodeProcessorMap.get(name);
        if(processor == null){
            throw new ValidateCodeException("验证码处理器"+name + "不存在");
        }
        return processor;
    }

}
