package com.Mike.user.Control;

import javax.servlet.http.HttpServletRequest;

import com.Mike.common.util.MyError;
import com.Mike.common.util.MyResponce;
import com.Mike.user.Control.Info.UserLoginInfo;
import com.Mike.user.Service.Interface.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControl {

    @Autowired
    private UserService userService;

    private MyResponce myResponce;

    // 登录和退出页面
    @PostMapping("login")
    public MyResponce Login(@RequestBody UserLoginInfo user) {
        try {
            String token = userService.login(user);
            myResponce = new MyResponce(token, 200, "登录成功！");
        } catch (MyError e) {
            myResponce = e.getMyResponce();
        }
        return myResponce;
    }

    // 注销登录
    @GetMapping("loginout")
    public MyResponce LoginOout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        try {
            userService.loginOut(token);
            myResponce = new MyResponce(null, 200, "退出成功！");
        } catch (MyError e) {
            myResponce = e.getMyResponce();
        }
        return myResponce;
    }

    // 测试是否在线
    @GetMapping("online")
    public MyResponce OnLine() {
        return new MyResponce(null, 200, "online");
    }

    @GetMapping("utype")
    public MyResponce uType(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        try {
            String utype = userService.uType(token);
            myResponce = new MyResponce(Integer.valueOf(utype), 200, "ok!");
        } catch (MyError e) {
            myResponce = e.getMyResponce();
        }
        return myResponce;
    }

    @GetMapping("file")
    public MyResponce file(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        try {
            Object data = userService.file(token);
            myResponce = new MyResponce(data, 200, "ok!");
        } catch (MyError e) {
            myResponce = e.getMyResponce();
        }
        return myResponce;
    }

    @PutMapping("savefile")
    public MyResponce savefile(HttpServletRequest request, String file) {
        String token = request.getHeader("Authorization");
        try {

            userService.savefile(token, file);
            myResponce = new MyResponce(null, 200, "ok!");
        } catch (MyError e) {
            myResponce = e.getMyResponce();
        }
        return myResponce;
    }
}
