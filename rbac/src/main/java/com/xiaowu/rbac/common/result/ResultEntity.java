package com.xiaowu.rbac.common.result;

import java.io.Serializable;

public class ResultEntity<T> implements Serializable {
    private int status;   // http 状态码
    private int code;    // 错误码(error code)
    private String msg;
    private T result;

    public ResultEntity() {
    }

    public ResultEntity(int status, int code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

    public ResultEntity(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultEntity(int code, int status, String msg, T result) {
        this.code = code;
        this.status = status;
        this.msg = msg;
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }


}
