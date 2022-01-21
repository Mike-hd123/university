package com.mike;

import java.sql.SQLException;

import com.mike.mapper.userinfomapper;
import com.mike.mapper.databaseobj.userinfo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoostrapApplicationTests {

    @Autowired
    userinfomapper userinfo;

    @Test
    void contextLoads() throws SQLException {
        userinfo a = userinfo.getuser("admin");
        System.out.println(a.getPasswd());
    }

}
