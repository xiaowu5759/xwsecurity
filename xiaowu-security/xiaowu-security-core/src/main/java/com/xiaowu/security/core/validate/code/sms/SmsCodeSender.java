package com.xiaowu.security.core.validate.code.sms;

public interface SmsCodeSender {

    // 使用jdk的默认实现
//    default void send(String mobile, String code){
//        System.out.println("向手机"+mobile+"发送验证码："+code);
//    }

    /**
     * @param mobile
     * @param code
     */
    void send(String mobile, String code);


}
