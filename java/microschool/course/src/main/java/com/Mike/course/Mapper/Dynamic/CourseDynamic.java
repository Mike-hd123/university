package com.Mike.course.Mapper.Dynamic;

import com.Mike.common.util.Search;

import org.apache.ibatis.jdbc.SQL;

public class CourseDynamic {
    public String search(Search search) {
        System.out.println(search);
        return new SQL() {
            {
                SELECT("*");
                FROM("course");
                LIMIT((search.getPagenum() - 1) * search.getPagesize() + "," + search.getPagesize());
                if (!" ".equals(search.getQuery())) {
                    AND();
                    WHERE("name LIKE '%" + search.getQuery() + "%'");
                }
            }
        }.toString();
    }

    public String gettotal(Search search) {
        System.out.println(search);
        return new SQL() {
            {
                SELECT("count(*)");
                FROM("course");
                if (!" ".equals(search.getQuery())) {
                    AND();
                    WHERE("name LIKE '%" + search.getQuery() + "%'");
                }
            }
        }.toString();
    }
}
