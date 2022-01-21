package com.mike.mapper.Provider;

import com.mike.conctrol.info.request.Search;

import org.apache.ibatis.jdbc.SQL;

public class coursesearch {
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
