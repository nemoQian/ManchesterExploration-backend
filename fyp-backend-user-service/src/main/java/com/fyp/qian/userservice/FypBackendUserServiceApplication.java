package com.fyp.qian.userservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.fyp.qian.userservice.mapper")
@ComponentScan("com.fyp.qian")
public class FypBackendUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FypBackendUserServiceApplication.class, args);
    }

}
