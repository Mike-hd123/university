package com.Mike.teacher.Control;

import javax.servlet.http.HttpServletRequest;

import com.Mike.common.util.MyError;
import com.Mike.common.util.MyResponce;
import com.Mike.common.util.Search;
import com.Mike.teacher.Control.Info.TeacherList;
import com.Mike.teacher.Mapper.Entity.Teacher;
import com.Mike.teacher.Service.Interface.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeacherControl {
    
    @Autowired
    private TeacherService teacherService;

    private MyResponce myResponce;

    @PostMapping() // 增
    public MyResponce add(@RequestBody Teacher teacher) {
        try {
            teacherService.add(teacher);
            myResponce = new MyResponce(null, 200, "成功！");
        } catch (MyError e) {
            myResponce = e.getMyResponce();
        }
        return myResponce;
    }

    @DeleteMapping() // 删
    public MyResponce del(Integer id) {
        try {
            teacherService.del(id);
            myResponce = new MyResponce(null, 200, "成功！");
        } catch (MyError e) {
            myResponce = e.getMyResponce();
        }
        return myResponce;
    }

    @PutMapping() // 改
    public MyResponce updata(@RequestBody Teacher teacher) {
        try {
            teacherService.updata(teacher);
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
            TeacherList list = teacherService.get(search);
            myResponce = new MyResponce(list, 200, "成功！");
        } catch (MyError e) {
            myResponce = e.getMyResponce();
        }
        return myResponce;
    }

    @GetMapping("me")
    public MyResponce me(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            Teacher teacher = teacherService.me(token);
            myResponce = new MyResponce(teacher, 200, "成功！");
        } catch (MyError e) {
            myResponce = e.getMyResponce();
        }
        return myResponce;
    }
}
