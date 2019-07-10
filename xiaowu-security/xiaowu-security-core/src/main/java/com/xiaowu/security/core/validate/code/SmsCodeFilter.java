package com.xiaowu.security.core.validate.code;

import com.xiaowu.security.core.properties.SecurityProperties;
import com.xiaowu.security.core.validate.code.image.ImageCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Slf4j
public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {
    // 里面这些初始化的private 利用securityconfig中传值过来

    // InitializingBean在其他参数都组装完毕的时候，才初始化url的值
    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    // string类型的set，存储需要拦截的url
    private Set<String> urls = new HashSet<>();

    private SecurityProperties securityProperties;

    private AntPathMatcher pathMatcher = new AntPathMatcher();


    // 覆盖InitializingBean的方法
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        // 将配置文件里面的url切开
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getSms().getUrl(),",");
        for (String configUrl : configUrls){
            urls.add(configUrl);
        }
        // 登录接口一定需要的验证码
        urls.add("/authentication/mobile");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        log.info("enter:{}",this.getClass());
//        if(StringUtils.equals("/authentication/form", request.getRequestURI()) && StringUtils.equalsIgnoreCase(request.getMethod(), "post"))
        log.info("enter:"+this.getClass());
        // 执行判断是否需要过滤
        boolean action = false;
        for (String url : urls){

            if(pathMatcher.match(url,request.getRequestURI())){
                action = true;
            }
        }

        if(action){
            log.info("enter if");
            try{
                // 作校验，从session中拿东西，校验工具需要ServletWebRequest参数，所以包装一下
                validate(new ServletWebRequest(request));
            }catch (ValidateCodeException ex){
                // 自定义异常,处理异常
                authenticationFailureHandler.onAuthenticationFailure(request,response,ex);
                // 直接返回
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        ValidateCode codeInSession = (ValidateCode)sessionStrategy.getAttribute(request,ValidateCodeProcessor.SESSION_KEY_PREFIX+"SMS");
        log.info(codeInSession.toString());

        // 从请求中 拿到name = imageCode
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),"smsCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException( "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }

        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX+"SMS");
            throw new ValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException( "验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX+"SMS");

    }
}
