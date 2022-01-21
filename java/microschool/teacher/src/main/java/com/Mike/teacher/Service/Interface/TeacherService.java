package com.Mike.teacher.Service.Interface;

import com.Mike.common.util.MyError;
import com.Mike.common.util.Search;
import com.Mike.teacher.Control.Info.TeacherList;
import com.Mike.teacher.Mapper.Entity.Teacher;

public interface TeacherService {
    public void add(Teacher teacher) throws MyError;

    public void del(Integer id) throws MyError;

    public void updata(Teacher teacher) throws MyError;

    public TeacherList get(Search search) throws MyError;

    public Teacher me(String token) throws MyError;
}
