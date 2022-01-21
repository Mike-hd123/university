package com.mike.conctrol;

import com.mike.conctrol.info.request.Search;
import com.mike.conctrol.info.responce.MyResponce;
import com.mike.conctrol.info.responce.TeacherList;
import com.mike.mapper.teachermapper;
import com.mike.mapper.databaseobj.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/teacher")
public class tearchctrol {
    @Autowired
    teachermapper teachermapper;

    @Autowired
    MyResponce responce;

    @Autowired
    TeacherList teacherlist;

    // 获取教师列表
    @GetMapping("")
    @ResponseBody
    public MyResponce get(@ModelAttribute Search search) {
        System.out.println(search);
        teacherlist.setList(teachermapper.search(search));
        teacherlist.setTotal(teachermapper.GetTotal(search));
        responce.setData(teacherlist);
        responce.setCode(200);
        responce.setMsg("ok");
        return responce;
    }

    @PutMapping("")
    @ResponseBody
    public MyResponce updata(@RequestBody teacher info) {
        System.out.print(info.getId());
        System.out.println(' ' + info.getUsername() + ' ' + info.getSsex() + ' ' + info.getSage());
        teachermapper.update(info);
        responce.setData(null);
        responce.setCode(200);
        responce.setMsg("ok");
        return responce;
    }

    @PostMapping("")
    @ResponseBody
    public MyResponce add(@RequestBody teacher info) {
        System.out.print(info.getId());
        System.out.println(' ' + info.getUsername() + ' ' + info.getSsex() + ' ' + info.getSage());
        System.out.println(teachermapper.add(info));
        responce.setData(null);
        responce.setCode(200);
        responce.setMsg("ok");
        return responce;
    }

    @DeleteMapping("")
    @ResponseBody
    public MyResponce dele(Integer id) {
        System.out.println(id);
        teachermapper.delete(id);
        responce.setData(null);
        responce.setCode(200);
        responce.setMsg("ok");
        return responce;
    }
}