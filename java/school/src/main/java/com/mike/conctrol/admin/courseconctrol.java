package com.mike.conctrol.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mike.mapper.coursemapper;
import com.mike.mapper.databaseobj.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/course")
public class courseconctrol {
    @Autowired
    coursemapper coursemapper;

    @RequestMapping("view")
    public String viewcourse(Model model) {
        model.addAttribute("courses", coursemapper.select());
        return "admin/course/view";
    }

    @RequestMapping("search")
    public String searchcourse(Model model, HttpServletRequest request) {
        String type = request.getParameter("type");
        String value = request.getParameter("value");
        List<course> courses = null;
        if (type != null && value != null) {
            if (type.equals("1")) {
                courses = coursemapper.searchbyid(Integer.parseInt(value));
            }
            if (type.equals("2")) {
                courses = coursemapper.searchbyname(value);
            }
            if (type.equals("3")) {
                courses = coursemapper.searchbycteacher(value);
            }
        }
        model.addAttribute("type", type);
        model.addAttribute("courses", courses);
        return "admin/course/search";
    }

    @GetMapping("add")
    public String addcourseview(Model model) {
        model.addAttribute("url", "/admin/course/add");
        model.addAttribute("funtion", "添加");
        return "admin/course/add";
    }

    @PostMapping("add")
    public String addcourse(course a) {
        coursemapper.add(a);
        return "redirect:/admin/course/view";
    }

    @GetMapping("chang")
    public String changcourse(Model model, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        model.addAttribute("url", "/admin/course/chang");
        model.addAttribute("funtion", "修改");
        model.addAttribute("course", coursemapper.searchbyidforchang(id));
        return "admin/course/add";
    }

    @PostMapping("chang")
    public String changcourse(course a) {
        coursemapper.update(a);
        return "redirect:/admin/course/view";
    }

    @GetMapping("delete")
    public String deletecourse(Model model, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        coursemapper.delete(id);
        return "redirect:/admin/course/view";
    }

}
