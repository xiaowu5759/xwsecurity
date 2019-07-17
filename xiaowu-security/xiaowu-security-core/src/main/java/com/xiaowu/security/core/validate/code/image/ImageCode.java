package com.xiaowu.security.core.validate.code.image;

import com.xiaowu.security.core.validate.code.ValidateCode;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

@Getter
@Setter
/**
 * 对象和属性，都需要实现序列化
 */
public class ImageCode extends ValidateCode {

    private BufferedImage image;  // 验证码图片

//    private String code;  // 随机数
//
//    private LocalDateTime expireTime;  // 过期时间

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image = image;
        // 在父类通过私有的方式定义了
//        this.code = code;
//        this.expireTime = expireTime;
    }

    // 构造60秒过期
    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code,expireIn);
        this.image = image;
//        this.code = code;
//        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }


}
