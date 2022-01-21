package com.mike.mapper;

import java.util.List;

import com.mike.conctrol.info.request.Search;
import com.mike.mapper.Provider.teachersearch;
import com.mike.mapper.databaseobj.teacher;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface teachermapper {
    // 动态sql
    @SelectProvider(type = teachersearch.class, method = "search")
    List<teacher> search(Search search);

    @SelectProvider(type = teachersearch.class, method = "gettotal")
    int GetTotal(Search search);

    // 保存头像
    @Update("update teacher set file=#{file} where id=#{id}")
    int savefile(teacher a);

    // 获取头像链接
    @Select("select file from teacher where id=#{id}")
    String getfile(int id);

    // 查询全部
    @Select("select * from teacher")
    List<teacher> select();

    // 通过指定值查询(为了便于列表处理，故统一返回list)
    @Select("select * from teacher where id=#{id}")
    List<teacher> searchbyid(int id);

    @Select("select * from teacher where username=#{username}")
    List<teacher> searchbyname(String username);

    @Select("select * from teacher where ssex=#{ssex}")
    List<teacher> searchbyssex(String ssex);

    @Select("select * from teacher where sage=#{sage}")
    List<teacher> searchbysage(String sage);
    // 返回单值用于修改

    @Select("select * from teacher where id=#{id}")
    teacher searchbyidforchang(int id);

    // 添加
    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("insert into teacher(username,ssex,sage) values(#{username},#{ssex},#{sage})")
    int add(teacher a);

    // 更新
    @Update("update teacher set username=#{username},ssex=#{ssex},sage=#{sage} where id=#{id}")
    int update(teacher a);

    // 删除
    @Delete("delete from teacher where id=#{id}")
    int delete(int id);
}
