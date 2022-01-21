package com.Mike.student.Service.Interface;

import com.Mike.common.util.MyError;
import com.Mike.common.util.Search;
import com.Mike.student.Control.Info.StudentList;
import com.Mike.student.Mapper.Entity.Student;

public interface StudentService {
    public void add(Student student) throws MyError;

    public void del(Integer id) throws MyError;

    public void updata(Student student) throws MyError;

    public StudentList get(Search search) throws MyError;

    public Student me(String token) throws MyError;
}
