package com.Mike.course.Service.Imp;

import com.Mike.common.util.MyError;
import com.Mike.common.util.Search;
import com.Mike.course.Control.Info.CourseList;
import com.Mike.course.Mapper.CourseMapper;
import com.Mike.course.Mapper.Entity.Course;
import com.Mike.course.Service.Interface.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImp implements CourseService {

    @Autowired
    private CourseMapper repository;

    @Override
    public void add(Course course) throws MyError {
        try {
            repository.add(course);
        } catch (Exception e) {
            throw new MyError(400, e.getMessage());
        }
    }

    @Override
    public void del(Integer id) throws MyError {
        try {
            repository.delete(id);
        } catch (Exception e) {
            throw new MyError(400, e.getMessage());
        }
    }

    @Override
    public void updata(Course course) throws MyError {
        try {
            repository.update(course);
        } catch (Exception e) {
            throw new MyError(400, e.getMessage());
        }
    }

    @Override
    public CourseList get(Search search) throws MyError {
        CourseList list = new CourseList();
        try {
            list.setList(repository.search(search));
            list.setTotal(repository.GetTotal(search));
        } catch (Exception e) {
            throw new MyError(400, e.getMessage());
        }
        return list;
    }
    
}
