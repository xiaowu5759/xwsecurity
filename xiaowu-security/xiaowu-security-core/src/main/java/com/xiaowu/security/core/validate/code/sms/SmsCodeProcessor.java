package com.xiaowu.security.core.validate.code.sms;

import com.xiaowu.security.core.validate.code.Impl.AbstractValidateCodeProcessor;
import com.xiaowu.security.core.validate.code.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    // 短信发送器，接口注进来了
    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode smsCode) throws Exception {
        // 发送出去
        // getRequiredStringParameter 请求中必须要包含moblie参数
        // getStringParameter
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(),"mobile");
        // 短信供应商，配置，调用发送方法
        smsCodeSender.send(mobile, smsCode.getCode());
    }
}
