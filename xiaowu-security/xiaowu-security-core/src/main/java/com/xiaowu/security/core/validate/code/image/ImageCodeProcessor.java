package com.xiaowu.security.core.validate.code.image;

import com.xiaowu.security.core.validate.code.Impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {
    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
        // 将生成的图片写到接口的响应中,写到响应的输出流中
        ImageIO.write(imageCode.getImage(),"PNG",request.getResponse().getOutputStream());
    }
}
