package com.mike.mapper;

import com.mike.mapper.databaseobj.admin;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface adminmapper {
    @Select("select file from admin where id=#{id}")
    String getfile(int id);

    @Update("update admin set file=#{filename} where id=#{id}")
    int savefile(admin a);
}
