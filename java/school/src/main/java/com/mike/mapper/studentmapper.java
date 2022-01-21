package com.mike.mapper;

import java.util.List;

import com.mike.mapper.databaseobj.student;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface studentmapper {
    // 查询全部
    @Result(column = "class", property = "classname")
    @Select("select * from student")
    List<student> select();

    // 通过指定值查询(为了便于列表处理，故统一返回list)
    @Result(column = "class", property = "classname")
    @Select("select * from student where id=#{id}")
    List<student> searchbyid(int id);

    @Result(column = "class", property = "classname")
    @Select("select * from student where username=#{username}")
    List<student> searchbyname(String username);

    @Result(column = "class", property = "classname")
    @Select("select * from student where ssex=#{ssex}")
    List<student> searchbyssex(String ssex);

    @Result(column = "class", property = "classname")
    @Select("select * from student where sage=#{sage}")
    List<student> searchbysage(String sage);

    @Result(column = "class", property = "classname")
    @Select("select * from student where class=#{classname}")
    List<student> searchbyClassname(String Classname);

    @Result(column = "class", property = "classname")
    @Select("select * from student where sdept=#{sdept}")
    List<student> searchbysdept(String sdept);

    //返回单值用于修改
    @Result(column = "class", property = "classname")
    @Select("select * from student where id=#{id}")
    student searchbyidforchang(int id);

    //添加
    @Options(useGeneratedKeys = true,keyColumn = "id")
    @Insert("insert into student(username,ssex,sage,class,sdept) values(#{username},#{ssex},#{sage},#{classname},#{sdept})")
    int add(student a);

    //更新
    @Update("update student set username=#{username},ssex=#{ssex},sage=#{sage},class=#{classname},sdept=#{sdept} where id=#{id}")
    int update(student a);

    //删除
    @Delete("delete from student where id=#{id}")
    int delete(int id);    
}
