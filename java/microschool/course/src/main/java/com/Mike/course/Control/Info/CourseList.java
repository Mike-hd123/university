package com.Mike.course.Control.Info;

import java.util.List;

import com.Mike.course.Mapper.Entity.Course;

import lombok.Data;

@Data
public class CourseList {
    private List<Course> list;
    private int total;
}
