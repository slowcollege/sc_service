package com.slow.college;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.slow.college.mapper")
public class SlowCollegeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlowCollegeApplication.class, args);
    }

}
