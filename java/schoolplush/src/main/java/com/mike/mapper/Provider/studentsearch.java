package com.mike.mapper.Provider;

import com.mike.conctrol.info.request.Search;

import org.apache.ibatis.jdbc.SQL;

public class studentsearch {
    public String search(Search search) {
        System.out.println(search);
        return new SQL() {
            {
                SELECT("*");
                FROM("student");
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
                FROM("student");
                if (!" ".equals(search.getQuery())) {
                    AND();
                    WHERE("username LIKE '%" + search.getQuery() + "%'");
                }
            }
        }.toString();
    }
}
