package com.mike.mapper.databaseobj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class student {
    private int id;
    private String username;
    private String ssex;
    private String sage;
    private String classname;
    private String sdept;
    private String file;

    public student(int id, String file) {
        this.id = id;
        this.file = file;
    }
}
