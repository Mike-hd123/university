package com.mike.mapper;

import com.mike.mapper.databaseobj.userinfo;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface userinfomapper {
    @Select("select * from userinfo where username=#{username}")
    userinfo getuser(String username);

    @Update("update userinfo set username=#{username},passwd=#{passwd} where id=#{id}")
    int update(userinfomapper a);
    // 删除
    @Delete("delete from userinfo where id=#{id}")
    int delete(int id);
    // 添加
    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("insert into userinfo(username,passwd,utype,uid) values(#{username},#{passwd},#{utype},#{uid})")
    int add(userinfomapper a);
}
