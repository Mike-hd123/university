package com.mike.mapper.databaseobj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class teacher {
    private int id;
    private String username;
    private String ssex;
    private int sage;
    private String file;

    public teacher(int id, String file) {
        this.id = id;
        this.file = file;
    }
}
