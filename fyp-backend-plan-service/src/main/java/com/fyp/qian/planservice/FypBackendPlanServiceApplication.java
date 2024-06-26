package com.fyp.qian.planservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.fyp.qian.serviceclient.service"})
public class FypBackendPlanServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FypBackendPlanServiceApplication.class, args);
    }

}
