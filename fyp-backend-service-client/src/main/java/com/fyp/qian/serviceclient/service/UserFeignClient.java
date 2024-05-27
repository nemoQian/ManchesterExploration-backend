package com.fyp.qian.serviceclient.service;

import com.fyp.qian.model.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@FeignClient(name = "fyp-backend-user-service", path = "/api/user/inner")
public interface UserFeignClient {

    /**
     *
     * @param userId
     * @return
     */
    @GetMapping("/get/id")
    User getUserById(@RequestParam("userId") long userId);

    /**
     *
     * @param ids
     * @return
     */
    @GetMapping("get/ids")
    List<User> listUserByIds(@RequestParam("ids") Collection<Long> ids);
}
