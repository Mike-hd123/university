package com.mike.conctrol;

import com.mike.conctrol.info.request.Search;
import com.mike.conctrol.info.responce.CourseList;
import com.mike.conctrol.info.responce.MyResponce;
import com.mike.mapper.coursemapper;
import com.mike.mapper.databaseobj.course;

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
@RequestMapping("api/course")
public class courseconctrol {
    @Autowired
    coursemapper coursemapper;

    @Autowired
    MyResponce responce;

    @Autowired
    CourseList courselist;

    // 获取课程列表
    @GetMapping("")
    @ResponseBody
    public MyResponce get(@ModelAttribute Search search) {
        courselist.setList(coursemapper.search(search));
        courselist.setTotal(coursemapper.GetTotal(search));
        responce.setData(courselist);
        responce.setCode(200);
        responce.setMsg("ok");
        return responce;
    }

    @PutMapping("")
    @ResponseBody
    public MyResponce updata(@RequestBody course info) {
        System.out.print(info.getId());
        coursemapper.update(info);
        responce.setData(null);
        responce.setCode(200);
        responce.setMsg("ok");
        return responce;
    }

    @PostMapping("")
    @ResponseBody
    public MyResponce add(@RequestBody course info) {
        System.out.print(info.getId());
        System.out.println(coursemapper.add(info));
        responce.setData(null);
        responce.setCode(200);
        responce.setMsg("ok");
        return responce;
    }

    @DeleteMapping("")
    @ResponseBody
    public MyResponce dele(Integer id) {
        System.out.print(id);
        coursemapper.delete(id);
        responce.setData(null);
        responce.setCode(200);
        responce.setMsg("ok");
        return responce;
    }
}
