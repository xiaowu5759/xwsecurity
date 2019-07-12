package com.xiaowu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class,args);
    }

    @RequestMapping("/hello")
//    redirect_uri=http%3A%2F%2Flocalhost%3A8060%2Fauth%2Fqq&state=e842a93a-555c-415e-aff1-f636097fe5d8
    // 修改host文件 www.pinzhi365.com
    // 9090
    // 所有发到80端口的，都被跳转到9090
    public String hello(){
        return "hello";
    }

    @RequestMapping("/user")
    public String user(){
        return "user";
    }

}

