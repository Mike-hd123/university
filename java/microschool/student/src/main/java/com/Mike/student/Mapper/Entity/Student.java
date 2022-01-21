package com.Mike.student.Mapper.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private int id;
    private String username;
    private String ssex;
    private String sage;
    private String classname;
    private String sdept;
}
