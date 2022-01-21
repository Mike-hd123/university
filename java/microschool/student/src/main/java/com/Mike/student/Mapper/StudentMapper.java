package com.Mike.student.Mapper;

import java.util.List;

import com.Mike.common.util.Search;
import com.Mike.student.Mapper.Dynamic.StudentDynamic;
import com.Mike.student.Mapper.Entity.Student;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMapper {
    // 动态sql
    @SelectProvider(type = StudentDynamic.class, method = "search")
    List<Student> search(Search search);

    @SelectProvider(type = StudentDynamic.class, method = "gettotal")
    int GetTotal(Search search);

    // 查询全部
    @Select("select * from student")
    List<Student> select();

    // 通过指定值查询(为了便于列表处理，故统一返回list)
    @Select("select * from student where id=#{id}")
    List<Student> searchbyid(int id);

    // 返回单值用于修改
    @Select("select * from student where id=#{id}")
    Student searchbyidforchang(int id);

    // 添加
    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("insert into student(username,ssex,sage,classname,sdept) values(#{username},#{ssex},#{sage},#{classname},#{sdept})")
    int add(Student student);

    // 更新
    @Update("update student set username=#{username},ssex=#{ssex},sage=#{sage},classname=#{classname},sdept=#{sdept} where id=#{id}")
    int update(Student student);

    // 删除
    @Delete("delete from student where id=#{id}")
    int delete(int id);
}
