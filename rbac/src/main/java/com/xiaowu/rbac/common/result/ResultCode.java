package com.xiaowu.rbac.common.result;

/**
 * 错误码
 * 默认 0 是 成功 SUCCESS
 * 1 是 用户输入错误 FAILURE
 * -1 是 系统错误 ERROR
 */
public enum ResultCode {
    SUCCESS(0, "成功"),
    FAILURE(1, "用户输入错误"),
    USERNAME_NOT_FOUND(2, "用户名未找到"),
    NOT_FOUND(3,"哎呀，页面不见了"),

    ERROR(-1, "系统错误")
    ;

    private final int code;
    private final String msg;

    ResultCode(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
