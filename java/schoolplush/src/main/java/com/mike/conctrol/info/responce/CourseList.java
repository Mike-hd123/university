package com.mike.conctrol.info.responce;

import java.util.List;

import com.mike.mapper.databaseobj.course;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class CourseList {
    List<course> list;
    int total;
}
