package com.Mike.user.Mapper.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    // 数据库中的id用来标识账号
    private int id;
    // 账号名称可与姓名不一样
    private String username;
    private String passwd;
    // 标识账号类型 1、管理员 2、教师 3、学生
    private int utype;
    // 对应数据库中的个人信息
    private int uid;
    // 用户头像
    private String file;
}
