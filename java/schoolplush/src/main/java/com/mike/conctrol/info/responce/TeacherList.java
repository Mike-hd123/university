package com.mike.conctrol.info.responce;

import java.util.List;

import com.mike.mapper.databaseobj.teacher;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class TeacherList {
    List<teacher> list;
    int total;
}
