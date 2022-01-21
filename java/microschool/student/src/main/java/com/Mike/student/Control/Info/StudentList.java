package com.Mike.student.Control.Info;

import java.util.List;

import com.Mike.student.Mapper.Entity.Student;

import lombok.Data;

@Data
public class StudentList {
    List<Student> list;
    int total;
}