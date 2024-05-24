package com.fyp.qian.userservice.controller;

import com.fyp.qian.common.common.BaseResponse;
import com.fyp.qian.common.common.ResponseResult;
import com.fyp.qian.model.enums.StateEnum;
import com.fyp.qian.model.pojo.User;
import com.fyp.qian.model.pojo.request.TagUserListRequest;
import com.fyp.qian.model.pojo.request.UserLoginRequest;
import com.fyp.qian.model.pojo.request.UserRegisterRequest;
import com.fyp.qian.model.pojo.request.UserUpdateRequest;
import com.fyp.qian.model.pojo.response.TagUserListResponse;
import com.fyp.qian.userservice.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @author Yihan Qian
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return ResponseResult.error(StateEnum.PARAMS_EMPTY_ERROR);
        }
        String username = userRegisterRequest.getUsername();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        long result = userService.userRegister(username, userPassword, checkPassword);

        return ResponseResult.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return ResponseResult.error(StateEnum.PARAMS_EMPTY_ERROR);
        }
        String username = userLoginRequest.getUsername();
        String userPassword = userLoginRequest.getUserPassword();

        User result = userService.userLogin(username, userPassword, request);

        return result != null ? ResponseResult.success(result) : ResponseResult.error(StateEnum.NO_RESULTS_ERROR);
    }

    @GetMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            return ResponseResult.error(StateEnum.PARAMS_EMPTY_ERROR);
        }
        int result = userService.userLogout(request);
        return ResponseResult.success(result);
    }

    @GetMapping("/searchUser")
    public BaseResponse<List<User>> searchUser(String username, HttpServletRequest request) {
        List<User> result = userService.usersList(username, request);

        return result != null ? ResponseResult.success(result) : ResponseResult.error(StateEnum.NO_RESULTS_ERROR);
    }

    @PostMapping("/deleteUser")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        boolean result = userService.userDelete(id, request);
        return ResponseResult.success(result);
    }

    @PostMapping("/userInfo")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        User result = userService.getCurrentUser(request);
        return result != null ? ResponseResult.success(result) : ResponseResult.error(StateEnum.NO_USER_ERROR);
    }

    @PostMapping("/updateUserInfo")
    public BaseResponse<Integer> updateUserInfo(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        String email = userUpdateRequest.getEmail();
        String phone = userUpdateRequest.getPhone();
        String nickname = userUpdateRequest.getNickname();
        String gender = userUpdateRequest.getGender();

        int result = userService.updateUserInfo(email, phone, nickname, gender, request);
        return ResponseResult.success(result);
    }

    @PostMapping("/updateAvatar")
    public BaseResponse<Integer> updateAvatar(@RequestParam("avatar") MultipartFile avatarFile, HttpServletRequest request) {
        int result = userService.updateUserAvatar(avatarFile, request);
        return ResponseResult.success(result);
    }

    @PostMapping("/updateUserTags")
    public BaseResponse<Integer> updateUserTags(@RequestBody List<String> tags, HttpServletRequest request) {
        int result = userService.updateUserTags(tags, request);
        return ResponseResult.success(result);
    }

    @GetMapping("/tagUserList")
    public BaseResponse<List<TagUserListResponse>> getTagUserList(TagUserListRequest tagUserListRequest) {
        List<TagUserListResponse> result = userService.searchUsersByTags(tagUserListRequest.getTags(), true);
        return ResponseResult.success(result);
    }
}
