package com.mike.config;

import com.mike.config.handle.adminhandle;
import com.mike.config.handle.loginhandle;
import com.mike.config.handle.studenthandle;
import com.mike.config.handle.teacherhandle;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class config implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //管理员页面拦截
        registry.addInterceptor(new adminhandle()).addPathPatterns("/admin/**");
        //教师
        registry.addInterceptor(new teacherhandle()).addPathPatterns("/teacher/**");
        //学生
        registry.addInterceptor(new studenthandle()).addPathPatterns("/student/**");
        //登录拦截
        registry.addInterceptor(new loginhandle()).addPathPatterns("/**").excludePathPatterns("/","/index.html","/login.html","/user/**","/index","/login","/images/**","/webjars/**","/test");
    }
}