package com.xiaowu.rbac.common.result;

public enum ResultStatus {
    OK(200, "OK")
    ;

    private final int value;

    private final String reasonPhrase;


    ResultStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int getValue() {
        return value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
