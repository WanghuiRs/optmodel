package com.shengao.optmodel.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
//    @Override
//    public void addResourceHandlers( ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] exclude = {"/vendor/**","/login","/userLogin"};
        registry.addInterceptor(new MyInterceptor()).excludePathPatterns(exclude);
    }

}
