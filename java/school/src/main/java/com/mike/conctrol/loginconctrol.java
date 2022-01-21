package com.mike.conctrol;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mike.mapper.userinfomapper;
import com.mike.mapper.databaseobj.userinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class loginconctrol {
    @Autowired
    userinfomapper userinfo;

    // 测试html页面
    @RequestMapping("test")
    public String test(Model model, HttpServletRequest request) {
        String username = request.getParameter("username");
        String age = request.getParameter("age");
        String sex = request.getParameter("sex");
        System.out.println(username + age + sex);
        return "test";
    }

    // 主页(未登录页面)
    @RequestMapping({ "", "index.html","login.html", "index", "login" })
    public String index() {
        return "login";
    }
    
    // 登录和退出页面
    @RequestMapping("user/login")
    public String login(@RequestParam("loginName") String username, @RequestParam("password") String passwd,
            Model model, HttpSession request) {
        userinfo a = userinfo.getuser(username);
        passwd = DigestUtils.md5DigestAsHex(passwd.getBytes());
        if (passwd.equals(a.getPasswd())) {
            // 保存登录信息
            request.setAttribute("userinfo", a);
            if (a.getUtype() == 1) {
                return "redirect:/admin/main";
            }
            if (a.getUtype() == 2) {
                return "redirect:/teacher/main";
            } else {
                return "redirect:/student/main";
            }
        } else {
            model.addAttribute("msg", "用户名或者密码错误！");
            // 只能返回index，这样才可以用模板引擎渲染，用redirect msg无效。
            return "index";
        }
    }

    @RequestMapping("user/logout")
    public String logout(HttpSession request) {
        request.invalidate();
        return "logout";
    }
}