package com.xiaowu.rbac.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义加密方式
 */

// NoOpPasswordEncoder 按原文处理
// StandardPasswordEncoder 1024次迭代的SHA-256散列哈希加密实现，并使用一个随机8字节的salt。
// BCryptPasswordEncoder 使用BCrypt的强散列哈希加密实现，并可以由客户端指定加密的强度strength，强度越高安全性自然就越高，默认为10.
@Component
public class MyPasswordEncoder implements PasswordEncoder {

    /**
     * 对密码进行加密并返回
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    /**
     * 验证密码是否正确
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(rawPassword.toString());
    }
}
