package com.fyp.qian.userservice.service.impl;

import com.fyp.qian.common.common.exception.BusinessException;
import com.fyp.qian.userservice.service.UserService;
import jakarta.annotation.Resource;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    void userRegister() {
        Assert.assertThrows(BusinessException.class, ()-> userService.userRegister("", "12345678a", "12345678a"));
        Assert.assertThrows(BusinessException.class, ()-> userService.userRegister("tes", "12345678a", "12345678a"));
        Assert.assertThrows(BusinessException.class, ()-> userService.userRegister("testRegister!", "12345678a", "12345678a"));
        Assert.assertThrows(BusinessException.class, ()-> userService.userRegister("testRegister", "123456", "12345678a"));
        Assert.assertThrows(BusinessException.class, ()-> userService.userRegister("testRegister", "12345678a", "123456"));
        Assert.assertThrows(BusinessException.class, ()-> userService.userRegister("testRegister", "123456789", "123456789"));
        Assert.assertThrows(BusinessException.class, ()-> userService.userRegister("systemAdmin", "12345678a", "12345678a"));

    }

    @Test
    void userLogin() {

        Assert.assertThrows(NullPointerException.class, ()-> userService.userLogin("systemAdmin", "12345678a", null));

    }

    @Test
    void userSearchByTag(){
//        List<String> tags = Arrays.asList("Tag 1", "Tag 2");
//        List<User> users = userService.searchUsersByTags(tags, true);
//        System.out.println(users.get(0));
//        Assert.assertNotNull(users);
    }

}