package com.mike.conctrol.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class admincontrol {
    @RequestMapping("main")
    public String main(){
        return "admin/main";
    }

    @RequestMapping("me")
    public String me(){
        return "admin/me";
    }

    @RequestMapping("passwd")
    public String passwd(){
        return "admin/passwd";
    }
}
