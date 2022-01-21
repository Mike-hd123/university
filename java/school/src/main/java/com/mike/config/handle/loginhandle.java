package com.mike.config.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class loginhandle implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getSession().getAttribute("userinfo") == null) {
            request.setAttribute("msg", "无权访问，请登录！");
            request.getRequestDispatcher("index.html").forward(request, response);
            return false;
        }
        return true;
    }
}
