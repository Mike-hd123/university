package com.Mike.teacher.Service.Imp;

import com.Mike.common.util.MyError;
import com.Mike.common.util.Search;
import com.Mike.teacher.Control.Info.TeacherList;
import com.Mike.teacher.Mapper.TeacherMapper;
import com.Mike.teacher.Mapper.Entity.Teacher;
import com.Mike.teacher.Service.Interface.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImp implements TeacherService {

    @Autowired
    private TeacherMapper repository;

    @Override
    public void add(Teacher teacher) throws MyError {
        try {
            repository.add(teacher);
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
    public void updata(Teacher teacher) throws MyError {
        try {
            repository.update(teacher);
        } catch (Exception e) {
            throw new MyError(400, e.getMessage());
        }
    }

    @Override
    public TeacherList get(Search search) throws MyError {
        TeacherList list = new TeacherList();
        try {
            list.setList(repository.search(search));
            list.setTotal(repository.GetTotal(search));
        } catch (Exception e) {
            throw new MyError(400, e.getMessage());
        }
        return list;
    }

    @Override
    public Teacher me(String token) throws MyError {
        Teacher teacher;
        try {
            teacher = null;
        } catch (Exception e) {
            throw new MyError(400, e.getMessage());
        }
        return teacher;
    }
    
}
