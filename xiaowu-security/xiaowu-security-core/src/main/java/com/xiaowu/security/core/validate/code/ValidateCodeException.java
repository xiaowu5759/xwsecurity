package com.xiaowu.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * 继承的是security中的异常基类
 */
public class ValidateCodeException extends AuthenticationException {


    public ValidateCodeException(String msg) {
        super(msg);
    }
}
