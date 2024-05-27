package com.fyp.qian.fypbackendgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FypBackendGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FypBackendGatewayApplication.class, args);
    }

}
