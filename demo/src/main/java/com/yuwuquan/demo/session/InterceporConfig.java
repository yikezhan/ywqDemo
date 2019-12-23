package com.yuwuquan.demo.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceporConfig implements WebMvcConfigurer {
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;
    static List<String> excludePathPatterns = new ArrayList<>();
    static{
        excludePathPatterns.add("/user/loginByPassword");
        excludePathPatterns.add("/user/loginByVerificationCode");
        excludePathPatterns.add("/user/loginOut");
        excludePathPatterns.add("/user/quickRegister");
        excludePathPatterns.add("/user/sendCode");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)// 注册拦截器
                .addPathPatterns("/**").excludePathPatterns(excludePathPatterns)
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");// 拦截所有请求
    }
}
