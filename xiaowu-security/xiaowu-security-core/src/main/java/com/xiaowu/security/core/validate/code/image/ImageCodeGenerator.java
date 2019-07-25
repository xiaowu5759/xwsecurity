package com.xiaowu.security.core.validate.code.image;

import com.xiaowu.security.core.properties.SecurityProperties;
import com.xiaowu.security.core.validate.code.ValidateCode;
import com.xiaowu.security.core.validate.code.ValidateCodeGenerator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


// 生成get，set方法，最后在配置文件中，给它set进去
// 这个时候就是 继承 抽象函数
@Component("imageValidateCodeGenerator")
public class ImageCodeGenerator implements ValidateCodeGenerator {
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        // 随机生成验证码的实现，网上copy
        // 之后需要重构代码
        // 设置成 可配置的；代码效率更高；结构更好

        // 利用spring.web自带的工具类，获取请求中的int值
        int width = ServletRequestUtils.getIntParameter(request.getRequest(),"width",securityProperties.getCode().getImage().getWidth());
        int height = ServletRequestUtils.getIntParameter(request.getRequest(),"height",securityProperties.getCode().getImage().getHeight());

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String sRand = "";
        for (int i = 0; i < securityProperties.getCode().getImage().getLength(); i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();

        return new ImageCode(image, sRand, securityProperties.getCode().getImage().getExpireIn());
    }

    /**
     * 生成随机背景条纹
     *
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if(fc > 255){
            fc = 255;
        }
        if (bc > 255){
            bc = 255;
        }
        int r = fc + random.nextInt(bc -fc);
        int g = fc + random.nextInt(bc -fc);
        int b = fc + random.nextInt(bc -fc);

        return new Color(r, g, b);

    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
