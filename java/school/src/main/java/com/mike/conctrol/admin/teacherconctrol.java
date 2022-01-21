package com.mike.conctrol.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mike.mapper.teachermapper;
import com.mike.mapper.databaseobj.tearcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/teacher")
public class teacherconctrol {
    @Autowired
    teachermapper teachermapper;

    @RequestMapping("/view")
    public String viewteacher(Model model){
        model.addAttribute("teachers",teachermapper.select());
        return "admin/teacher/view";
    }
    
    @RequestMapping("/search")
    public String searchteacher(Model model,HttpServletRequest request){
        String type = request.getParameter("type");
        String value = request.getParameter("value");
        List<tearcher> tearchers=null;
        if(type!=null && value!=null){
            if(type.equals("1")){
                tearchers=teachermapper.searchbyid(Integer.parseInt(value));
            }if(type.equals("2")){
                tearchers=teachermapper.searchbyname(value);   
            }if(type.equals("3")){
                tearchers=teachermapper.searchbyssex(value);
            }if(type.equals("4")){
                tearchers=teachermapper.searchbysage(value);
            }
        }  
        model.addAttribute("type", type);
        model.addAttribute("teachers",tearchers);
        return "admin/teacher/search";
    }

    @GetMapping("/add")
    public String addteacherview(Model model){
        model.addAttribute("url", "/admin/teacher/add");
        model.addAttribute("funtion", "添加");
        return "admin/teacher/add";
    }

    @PostMapping("/add")
    public String addteacher(tearcher a){
        teachermapper.add(a);
        return "redirect:/admin/teacher/view";
    }

    @GetMapping("/chang")
    public String changteacher(Model model,HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        model.addAttribute("url", "/admin/teacher/chang");
        model.addAttribute("funtion", "修改");
        model.addAttribute("teacher", teachermapper.searchbyidforchang(id));
        return "admin/teacher/add";
    }

    @PostMapping("/chang")
    public String changteacher(tearcher a){
        teachermapper.update(a);
        return "redirect:/admin/teacher/view";
    }

    @GetMapping("/delete")
    public String deleteteacher(Model model,HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        teachermapper.delete(id);
        return "redirect:/admin/teacher/view";
    }
}
