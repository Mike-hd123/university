package com.Mike.course.Control;

import com.Mike.common.util.MyError;
import com.Mike.common.util.MyResponce;
import com.Mike.common.util.Search;
import com.Mike.course.Control.Info.CourseList;
import com.Mike.course.Mapper.Entity.Course;
import com.Mike.course.Service.Interface.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseControl {
    
    @Autowired
    private CourseService courseService;

    private MyResponce myResponce;

    @PostMapping() // 增
    public MyResponce add(@RequestBody Course course) {
        try {
            courseService.add(course);
            myResponce = new MyResponce(null, 200, "成功！");
        } catch (MyError e) {
            myResponce = e.getMyResponce();
        }
        return myResponce;
    }

    @DeleteMapping() // 删
    public MyResponce del(Integer id) {
        try {
            courseService.del(id);
            myResponce = new MyResponce(null, 200, "成功！");
        } catch (MyError e) {
            myResponce = e.getMyResponce();
        }
        return myResponce;
    }

    @PutMapping() // 改
    public MyResponce updata(@RequestBody Course course) {
        try {
            courseService.updata(course);
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
            CourseList list = courseService.get(search);
            myResponce = new MyResponce(list, 200, "成功！");
        } catch (MyError e) {
            myResponce = e.getMyResponce();
        }
        return myResponce;
    }
}
