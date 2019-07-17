package com.xiaowu.security.core.validate.code;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

// 修改逻辑，采用继承关系
// 提高代码整洁性
@Data
public class ValidateCode implements Serializable {

    private String code;

    private LocalDateTime expireTime;

    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
