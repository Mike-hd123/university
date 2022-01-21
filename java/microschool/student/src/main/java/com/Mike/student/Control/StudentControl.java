package com.Mike.student.Control;

import javax.servlet.http.HttpServletRequest;

import com.Mike.common.util.MyError;
import com.Mike.common.util.MyResponce;
import com.Mike.common.util.Search;
import com.Mike.student.Control.Info.StudentList;
import com.Mike.student.Mapper.Entity.Student;
import com.Mike.student.Service.Interface.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentControl {

    @Autowired
    private StudentService studentService;

    private MyResponce myResponce;

    @PostMapping() // 增
    public MyResponce add(@RequestBody Student student) {
        try {
            studentService.add(student);
            myResponce = new MyResponce(null, 200, "成功！");
        } catch (MyError e) {
            myResponce = e.getMyResponce();
        }
        return myResponce;
    }

    @DeleteMapping() // 删
    public MyResponce del(Integer id) {
        try {
            studentService.del(id);
            myResponce = new MyResponce(null, 200, "成功！");
        } catch (MyError e) {
            myResponce = e.getMyResponce();
        }
        return myResponce;
    }

    @PutMapping() // 改
    public MyResponce updata(@RequestBody Student student) {
        try {
            studentService.updata(student);
            myResponce = new MyResponce(null, 200, "成功！");
        } catch (MyError e) {
            myResponce = e.getMyResponce();
        }
        return myResponce;
    }

    // 获取学生列表
    @GetMapping() // 查
    public MyResponce get(@ModelAttribute Search search) {
        try {
            StudentList list = studentService.get(search);
            myResponce = new MyResponce(list, 200, "成功！");
        } catch (MyError e) {
            myResponce = e.getMyResponce();
        }
        return myResponce;
    }

    @GetMapping("me")
    public MyResponce me(HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization");
            Student student = studentService.me(token);
            myResponce = new MyResponce(student, 200, "成功！");
        } catch (MyError e) {
            myResponce = e.getMyResponce();
        }
        return myResponce;
    }
}
