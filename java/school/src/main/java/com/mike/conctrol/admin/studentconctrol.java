package com.mike.conctrol.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mike.mapper.studentmapper;
import com.mike.mapper.databaseobj.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/student")
public class studentconctrol {
    @Autowired
    studentmapper studentmapper;

    @RequestMapping("view")
    public String viewstudent(Model model) {
        model.addAttribute("students", studentmapper.select());
        return "admin/student/view";
    }

    @RequestMapping("search")
    public String searchstudent(Model model, HttpServletRequest request) {
        String type = request.getParameter("type");
        String value = request.getParameter("value");
        List<student> students = null;
        if (type != null && value != null) {
            if (type.equals("1")) {
                students = studentmapper.searchbyid(Integer.parseInt(value));
            }
            if (type.equals("2")) {
                students = studentmapper.searchbyname(value);
            }
            if (type.equals("3")) {
                students = studentmapper.searchbyssex(value);
            }
            if (type.equals("4")) {
                students = studentmapper.searchbysage(value);
            }
            if (type.equals("5")) {
                students = studentmapper.searchbyClassname(value);
            }
            if (type.equals("6")) {
                students = studentmapper.searchbysdept(value);
            }
        }
        model.addAttribute("type", type);
        model.addAttribute("students", students);
        return "admin/student/search";
    }

    @GetMapping("add")
    public String addstudentview(Model model) {
        model.addAttribute("url", "/admin/student/add");
        model.addAttribute("funtion", "添加");
        return "admin/student/add";
    }

    @PostMapping("add")
    public String addstudent(student a) {
        studentmapper.add(a);
        return "redirect:/admin/student/view";
    }

    @GetMapping("chang")
    public String changstudent(Model model, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        model.addAttribute("url", "/admin/student/chang");
        model.addAttribute("funtion", "修改");
        model.addAttribute("student", studentmapper.searchbyidforchang(id));
        return "admin/student/add";
    }

    @PostMapping("chang")
    public String changstudent(student a) {
        studentmapper.update(a);
        return "redirect:/admin/student/view";
    }

    @GetMapping("delete")
    public String deletestudent(Model model, HttpServletRequest request) {
    int id = Integer.parseInt(request.getParameter("id"));
    studentmapper.delete(id);
    return "redirect:/admin/student/view";
    }

}
