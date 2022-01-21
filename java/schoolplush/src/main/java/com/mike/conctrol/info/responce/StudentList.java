package com.mike.conctrol.info.responce;

import java.util.List;

import com.mike.mapper.databaseobj.student;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class StudentList {
    List<student> list;
    int total;
}
