package com.Mike.student.Service.Imp;

import com.Mike.common.util.MyError;
import com.Mike.common.util.Search;
import com.Mike.student.Control.Info.StudentList;
import com.Mike.student.Mapper.StudentMapper;
import com.Mike.student.Mapper.Entity.Student;
import com.Mike.student.Service.Interface.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImp implements StudentService{

    @Autowired
    private StudentMapper repository;

    @Override
    public void add(Student student) throws MyError {
        try {
            repository.add(student);
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
    public void updata(Student student) throws MyError {
        try {
            repository.update(student);
        } catch (Exception e) {
            throw new MyError(400, e.getMessage());
        }
    }

    @Override
    public StudentList get(Search search) throws MyError {
        StudentList list = new StudentList();
        try {
            list.setList(repository.search(search));
            list.setTotal(repository.GetTotal(search));
        } catch (Exception e) {
            throw new MyError(400, e.getMessage());
        }
        return list;
    }

    @Override
    public Student me(String token) throws MyError {
        Student student;
        try {
            student = null;
        } catch (Exception e) {
            throw new MyError(400, e.getMessage());
        }
        return student;
    }
    
}
