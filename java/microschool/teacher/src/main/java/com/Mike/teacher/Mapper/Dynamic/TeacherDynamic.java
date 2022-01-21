package com.Mike.teacher.Mapper.Dynamic;

import com.Mike.common.util.Search;

import org.apache.ibatis.jdbc.SQL;

public class TeacherDynamic {
    public String search(Search search) {
        System.out.println(search);
        return new SQL() {
            {
                SELECT("*");
                FROM("teacher");
                LIMIT((search.getPagenum() - 1) * search.getPagesize() + "," + search.getPagesize());
                if (!" ".equals(search.getQuery())) {
                    AND();
                    WHERE("username LIKE '%" + search.getQuery() + "%'");
                }
            }
        }.toString();
    }

    public String gettotal(Search search) {
        System.out.println(search);
        return new SQL() {
            {
                SELECT("count(*)");
                FROM("teacher");
                if (!" ".equals(search.getQuery())) {
                    AND();
                    WHERE("username LIKE '%" + search.getQuery() + "%'");
                }
            }
        }.toString();
    }
}
