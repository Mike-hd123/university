package com.Mike.teacher;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.Mike.teacher.Mapper")
public class TeacherApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeacherApplication.class, args);
	}

}
