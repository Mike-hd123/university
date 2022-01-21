package com.mike;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mike.mapper")
public class BoostrapApplication {
    public static void main(String[] args) {
        SpringApplication.run(BoostrapApplication.class, args);
    }

}
