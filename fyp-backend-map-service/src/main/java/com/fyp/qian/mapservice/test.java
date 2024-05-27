package com.fyp.qian.mapservice;

import com.fyp.qian.serviceclient.service.UserFeignClient;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class test {

    @Resource
    private UserFeignClient userFeignClient;

    @GetMapping("/test")
    public void a() {
        userFeignClient.getUserById(1L);
    }
}
