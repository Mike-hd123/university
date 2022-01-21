package com.Mike.teacher.Control.Info;

import java.util.List;

import com.Mike.teacher.Mapper.Entity.Teacher;

import lombok.Data;

@Data
public class TeacherList {
    List<Teacher> list;
    int total;
}
