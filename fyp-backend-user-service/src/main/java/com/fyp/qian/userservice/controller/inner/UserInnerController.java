package com.fyp.qian.userservice.controller.inner;

import com.fyp.qian.model.pojo.User;
import com.fyp.qian.serviceclient.service.UserFeignClient;
import com.fyp.qian.userservice.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("inner")
public class UserInnerController implements UserFeignClient {
    @Resource
    private UserService userService;

    @Override
    @GetMapping("/get/id")
    public User getUserById(@RequestParam("userId") long userId) {
        return userService.getById(userId);
    }

    @Override
    public @GetMapping("get/ids")
    List<User> listUserByIds(@RequestParam("ids") Collection<Long> ids) {
        return userService.listByIds(ids);
    }
}
