package com.fyp.qian.userservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.fyp.qian.userservice.mapper")
@ComponentScan("com.fyp.qian")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.fyp.qian.serviceclient.service"})
public class FypBackendUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FypBackendUserServiceApplication.class, args);
    }

}
