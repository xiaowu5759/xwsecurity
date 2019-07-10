package com.xiaowu.security.core.validate.code;


import com.xiaowu.security.core.validate.code.image.ImageCode;
import com.xiaowu.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class ValidateCodeController {

    // 将以前的两个服务合并成一个
    // 校验码处理器
    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessorMap;

    /**
     * 创建验证码，根据校验码类型不同
     * 调用不同的{@link ValidateCodeProcessor}接口实现
     * @param response
     * @param request
     * @param type
     * @throws Exception
     */
    @RequestMapping(value = "/code/{type}",method = RequestMethod.GET)
    public void createCode(HttpServletResponse response, HttpServletRequest request, @PathVariable String type) throws Exception {
        validateCodeProcessorMap.get(type+"ValidateCodeProcessor").create(new ServletWebRequest(request,response));
    }



//    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

//    // 需要security的工具类
//    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
//
//    @Autowired
//    @Qualifier("imageCodeGenerator")
//    private ValidateCodeGenerator imageCodeGenerator;
//
//    @Autowired
//    private ValidateCodeGenerator smsCodeGenerator;
//
//    @Autowired
//    private SmsCodeSender smsCodeSender;
//
//    @RequestMapping(value = "/code/image", method = RequestMethod.GET)
//    public void createImageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(new ServletWebRequest(request));
//        // 保存再session中
//        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,imageCode);
//        // 将生成的图片写到接口的响应中,写到响应的输出流中
//        ImageIO.write(imageCode.getImage(),"PNG",response.getOutputStream());
//    }
//
//    @RequestMapping(value = "/code/image", method = RequestMethod.GET)
//    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
//        ValidateCode smsCode = smsCodeGenerator.generate(new ServletWebRequest(request));
//        // 保存再session中
//        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,smsCode);
//        // 主干逻辑一致的，但是实现方法不一样的，一般使用模板方法把他抽象出来
//        // 发送出去
//        // getRequiredStringParameter 请求中必须要包含moblie参数
//        // getStringParameter
//        String mobile = ServletRequestUtils.getRequiredStringParameter(request,"moblie");
//        // 短信供应商，配置，调用发送方法
//        smsCodeSender.send(mobile, smsCode.getCode());
//
//    }

}
