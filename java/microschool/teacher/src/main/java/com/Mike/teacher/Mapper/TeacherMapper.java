package com.Mike.teacher.Mapper;

import java.util.List;

import com.Mike.common.util.Search;
import com.Mike.teacher.Mapper.Dynamic.TeacherDynamic;
import com.Mike.teacher.Mapper.Entity.Teacher;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherMapper {
    // 添加
    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("insert into teacher(username,ssex,sage) values(#{username},#{ssex},#{sage})")
    int add(Teacher teacher);
    
    // 删除
    @Delete("delete from teacher where id=#{id}")
    int delete(int id);
    
    // 更新
    @Update("update teacher set username=#{username},ssex=#{ssex},sage=#{sage} where id=#{id}")
    int update(Teacher teacher);

    // 动态sql
    @SelectProvider(type = TeacherDynamic.class, method = "search")
    List<Teacher> search(Search search);

    @SelectProvider(type = TeacherDynamic.class, method = "gettotal")
    int GetTotal(Search search);
}
