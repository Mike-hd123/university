package com.mike.conctrol;

import com.mike.conctrol.info.request.Search;
import com.mike.conctrol.info.responce.MyResponce;
import com.mike.conctrol.info.responce.StudentList;
import com.mike.mapper.studentmapper;
import com.mike.mapper.databaseobj.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/student")
public class studentconctrol {
    @Autowired
    studentmapper studentmapper;

    @Autowired
    MyResponce responce;

    @Autowired
    StudentList studentlist;

    // 获取学生列表
    @GetMapping()
    public MyResponce get(@ModelAttribute Search search) {
        studentlist.setList(studentmapper.search(search));
        studentlist.setTotal(studentmapper.GetTotal(search));
        responce.setData(studentlist);
        responce.setCode(200);
        responce.setMsg("ok");
        return responce;
    }

    @PutMapping()
    public MyResponce updata(@RequestBody student info) {
        System.out.print(info.getId());
        System.out.println(' ' + info.getUsername() + ' ' + info.getSsex() + ' ' + info.getSage());
        studentmapper.update(info);
        responce.setData(null);
        responce.setCode(200);
        responce.setMsg("ok");
        return responce;
    }

    @PostMapping()
    public MyResponce add(@RequestBody student info) {
        System.out.print(info.getId());
        System.out.println(' ' + info.getUsername() + ' ' + info.getSsex() + ' ' + info.getSage());
        System.out.println(studentmapper.add(info));
        responce.setData(null);
        responce.setCode(200);
        responce.setMsg("ok");
        return responce;
    }

    @DeleteMapping()
    public MyResponce dele(Integer id) {
        System.out.println("delete id:" + id);
        studentmapper.delete(id);
        responce.setData(null);
        responce.setCode(200);
        responce.setMsg("ok");
        return responce;
    }
}
