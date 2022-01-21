package com.Mike.course.Mapper;

import java.util.List;

import com.Mike.common.util.Search;
import com.Mike.course.Mapper.Dynamic.CourseDynamic;
import com.Mike.course.Mapper.Entity.Course;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseMapper {
    // 添加
    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("insert into course(name,cteacher) values(#{name},#{cteacher})")
    int add(Course course);
    
    // 删除
    @Delete("delete from course where id=#{id}")
    int delete(int id);

    // 更新
    @Update("update course set name=#{name},cteacher=#{cteacher} where id=#{id}")
    int update(Course course);

    // 动态sql
    @SelectProvider(type = CourseDynamic.class, method = "search")
    List<Course> search(Search search);

    @SelectProvider(type = CourseDynamic.class, method = "gettotal")
    int GetTotal(Search search);
}
