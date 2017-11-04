package com.heshan.springboot.web.platform.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.heshan.springboot.web.platform.api.interceptor.AuthorizationInterceptor;
import com.heshan.springboot.web.platform.api.resolver.LoginUserHandlerMethodArgumentResolver;

/**
 * MVC配置
 *
 * @author
 * @email
 * @date 2017-04-20 22:30
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;
    //    @Autowired
//    private RequestLoggerInterceptor requestLoggerInterceptor;
    @Autowired
    private LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(authorizationInterceptor).addPathPatterns("/api/**");
        //registry.addInterceptor(requestLoggerInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserHandlerMethodArgumentResolver);
    }

}