package com.fyp.qian.mapservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.fyp.qian.mapservice.mapper")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.fyp.qian.serviceclient.service"})
public class FypBackendMapServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FypBackendMapServiceApplication.class, args);
    }

}
