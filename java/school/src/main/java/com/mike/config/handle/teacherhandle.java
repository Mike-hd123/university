package com.mike.config.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mike.mapper.databaseobj.userinfo;

import org.springframework.web.servlet.HandlerInterceptor;

public class teacherhandle implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
                userinfo a = (userinfo) request.getSession().getAttribute("userinfo");
                if (a.getUtype() == 2 && request.getRequestURL().indexOf("teacher") != -1) {
                    return true;
                }
                return false;
    }
}
