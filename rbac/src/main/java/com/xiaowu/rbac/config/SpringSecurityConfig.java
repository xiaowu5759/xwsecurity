package com.xiaowu.rbac.config;

import com.xiaowu.rbac.security.*;
import com.xiaowu.rbac.security.handller.MyAuthenticationFailureHandler;
import com.xiaowu.rbac.security.handller.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
// 自己配置
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyPasswordEncoder myPasswordEncoder;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    MyAuthenticationProvider myAuthenticationProvider;

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;


    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

//    @Bean
//    GrantedAuthorityDefaults grantedAuthorityDefaults() {
//        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
//    }

//    //@Bean 把实例化的对象转化成一个Bean，放在Ioc容器中，会和@Autowired配合到一起，把对象、属性、方法完美封装。
//    @Bean
//    UserDetailsService myUserDetailsService() {
//        return new MyUserDetailsService();
//    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(myUserDetailsService).passwordEncoder(myPasswordEncoder);
        //注册AuthenticationProvider
        auth.authenticationProvider(myAuthenticationProvider);
    }

    // 目标是这边不出现权限管理
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/error").permitAll()
//                .antMatchers("/").permitAll()
//                .antMatchers("/login").permitAll()
                .anyRequest().permitAll(); //任何请求,都放过，这样就对了
//                .and()
//                .formLogin()
//                    .loginProcessingUrl("/auth/login")
//                    .successHandler(myAuthenticationSuccessHandler)
//                    .failureHandler(myAuthenticationFailureHandler)
//                    .permitAll(); //登录页面用户任意访问

        http.addFilterAfter(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        // 添加自定义的filter，处理用户登录的数据
        http.addFilterBefore(myUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    // 自己注入bean
    @Bean
    MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter() throws  Exception {
        MyUsernamePasswordAuthenticationFilter filter = new MyUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        // 设置登录的URL
        filter.setFilterProcessesUrl("/auth/login");

        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
}
