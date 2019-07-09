package com.xiaowu.security.core.validate.code;

import com.xiaowu.security.core.properties.SecurityProperties;
import com.xiaowu.security.core.validate.code.image.ImageCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
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
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Slf4j
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
    // 里面这些初始化的private 利用securityconfig中传值过来
    // InitializingBean在其他参数都组装完毕的时候，才初始化url的值

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    // 系统校验码处理器（模板方法）
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    // 存放所有需要校验码的 url
    private Map<String, ValidateCodeType> urlMap = new HashedMap<>();

    // 验证请求url与配置的url是否匹配的工具类
    private AntPathMatcher pathMatcher = new AntPathMatcher();


    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    // string类型的set，存储需要拦截的url
    private Set<String> urls = new HashSet<>();

    private SecurityProperties securityProperties;


    /**
     * 初始化要拦截的url配置信息
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        // 将配置文件里面的url切开
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(),",");
        for (String configUrl : configUrls){
            urls.add(configUrl);
        }
        // 登录接口一定需要的验证码
        urls.add("/authentication/form");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        log.info("enter:{}",this.getClass());
//        if(StringUtils.equals("/authentication/form", request.getRequestURI()) && StringUtils.equalsIgnoreCase(request.getMethod(), "post"))
        // 执行判断是否需要过滤
        boolean action = false;
        for (String url : urls){
            if(pathMatcher.match(url,request.getRequestURI())){
                action = true;
            }
        }

        if(action){
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
        ImageCode codeInSession = (ImageCode)sessionStrategy.getAttribute(request,ValidateCodeProcessor.SESSION_KEY_PREFIX+"IMAGE");

        // 从请求中 拿到name = imageCode
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),"imageCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException( "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }

        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX+"IMAGE");
            throw new ValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException( "验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX+"IMAGE");

    }
}
