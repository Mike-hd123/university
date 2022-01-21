package com.Mike.user.Mapper;

import com.Mike.user.Mapper.Entity.User;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    @Select("select * from user where username=#{name}")
    User getuser(String name);

    @Update("update user set file=#{file},passwd=#{passwd} where id=#{id}")
    int update(User user);

    // 删除
    @Delete("delete from user where id=#{id}")
    int delete(int id);

    // 添加
    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("insert into user(username,passwd,utype,uid) values(#{username},#{passwd},#{utype},#{uid})")
    int add(User user);
}
