package com.Mike.course.Service.Interface;

import com.Mike.common.util.MyError;
import com.Mike.common.util.Search;
import com.Mike.course.Control.Info.CourseList;
import com.Mike.course.Mapper.Entity.Course;

public interface CourseService {
    public void add(Course course) throws MyError;

    public void del(Integer id) throws MyError;

    public void updata(Course course) throws MyError;

    public CourseList get(Search search) throws MyError;
}
