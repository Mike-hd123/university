package com.mike.conctrol;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("student")
public class studentcontrol {
    @GetMapping("{page}")
    public String all(@PathVariable("page")String page){
        return("student/"+page);
    }
}
