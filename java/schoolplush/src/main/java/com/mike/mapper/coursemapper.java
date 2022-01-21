package com.mike.mapper;

import java.util.List;

import com.mike.conctrol.info.request.Search;
import com.mike.mapper.Provider.coursesearch;
import com.mike.mapper.databaseobj.course;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface coursemapper {
    // 动态sql
    @SelectProvider(type = coursesearch.class, method = "search")
    List<course> search(Search search);

    @SelectProvider(type = coursesearch.class, method = "gettotal")
    int GetTotal(Search search);

    // 查询全部
    @Select("select * from course")
    List<course> select();

    // 通过指定值查询(为了便于列表处理，故统一返回list)
    @Select("select * from course where id=#{id}")
    List<course> searchbyid(int id);

    @Select("select * from course where name=#{name}")
    List<course> searchbyname(String name);

    @Select("select * from course where cteacher=#{cteacher}")
    List<course> searchbycteacher(String cteacher);

    // 返回单值用于修改
    @Select("select * from course where id=#{id}")
    course searchbyidforchang(int id);

    // 添加
    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("insert into course(name,cteacher) values(#{name},#{cteacher})")
    int add(course a);

    // 更新
    @Update("update course set name=#{name},cteacher=#{cteacher} where id=#{id}")
    int update(course a);

    // 删除
    @Delete("delete from course where id=#{id}")
    int delete(int id);

}
