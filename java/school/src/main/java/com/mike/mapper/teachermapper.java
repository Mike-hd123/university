package com.mike.mapper;

import java.util.List;

import com.mike.mapper.databaseobj.tearcher;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface teachermapper {
    //查询全部
    @Select("select * from teacher")
    List<tearcher> select();
    //通过指定值查询(为了便于列表处理，故统一返回list)
    @Select("select * from teacher where id=#{id}")
    List<tearcher> searchbyid(int id);
    @Select("select * from teacher where username=#{username}")
    List<tearcher> searchbyname(String username);
    @Select("select * from teacher where ssex=#{ssex}")
    List<tearcher> searchbyssex(String ssex);
    @Select("select * from teacher where sage=#{sage}")
    List<tearcher> searchbysage(String sage);
    //返回单值用于修改
    @Select("select * from teacher where id=#{id}")
    tearcher searchbyidforchang(int id);
    //添加
    @Options(useGeneratedKeys = true,keyColumn = "id")
    @Insert("insert into teacher(username,ssex,sage) values(#{username},#{ssex},#{sage})")
    int add(tearcher a);
    //更新
    @Update("update teacher set username=#{username},ssex=#{ssex},sage=#{sage} where id=#{id}")
    int update(tearcher a);
    //删除
    @Delete("delete from teacher where id=#{id}")
    int delete(int id);
}
