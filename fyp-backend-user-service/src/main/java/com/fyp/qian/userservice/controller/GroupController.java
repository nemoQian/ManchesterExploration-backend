package com.fyp.qian.userservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.qian.common.common.BaseResponse;
import com.fyp.qian.common.common.ResponseResult;
import com.fyp.qian.model.pojo.UserGroup;
import com.fyp.qian.model.pojo.request.GroupSearchRequest;
import com.fyp.qian.userservice.service.UserGroupService;
import com.fyp.qian.userservice.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Resource
    private UserGroupService userGroupService;

    @Resource
    private UserService userService;

    @PostMapping("/addGroup")
    public BaseResponse<Long> addGroup(@RequestBody UserGroup userGroup, HttpServletRequest request) {
        Long result = userGroupService.addGroup(userGroup, request);
        return ResponseResult.success(result);
    }

    @PostMapping("/list")
    public BaseResponse<Page<UserGroup>> listGroup(@RequestBody GroupSearchRequest groupSearchRequest) {
        return ResponseResult.success(userGroupService.listGroup(groupSearchRequest));
    }
}
