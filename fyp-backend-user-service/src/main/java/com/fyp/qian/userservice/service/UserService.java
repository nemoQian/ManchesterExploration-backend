package com.fyp.qian.userservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fyp.qian.model.pojo.User;
import com.fyp.qian.model.pojo.request.TagUserListRequest;
import com.fyp.qian.model.pojo.response.TagUserListResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

/**
* @author Yihan Qian
* @description 针对表【user(user_table)】的数据库操作Service
* @createDate 2024-04-18 19:26:43
*/
public interface UserService extends IService<User> {

    /**
     *
     * @param username
     * @param userPassword
     * @param checkPassword
     * @return
     */
    long userRegister(String username, String userPassword, String checkPassword);

    /**
     *
     * @param username
     * @param userPassword
     * @param request
     * @return
     */
    User userLogin(String username, String userPassword, HttpServletRequest request);

    /**
     *
     * @param username
     * @param request
     * @return
     */
    List<User> usersList(String username, HttpServletRequest request);

    /**
     *
     * @param id
     * @param request
     * @return
     */
    boolean userDelete(long id, HttpServletRequest request);

    /**
     *
     * @param request
     * @return
     */
    User getCurrentUser(HttpServletRequest request);

    /**
     *
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

    /**
     *
     * @param tagUserListRequest
     * @param strict
     * @return
     */
    List<TagUserListResponse> searchUsersByTags(TagUserListRequest tagUserListRequest, boolean strict);

    /**
     *
     * @param email
     * @param phone
     * @param nickname
     * @param gender
     * @param request
     * @return
     */
    int updateUserInfo(String email, String phone, String nickname, String gender, HttpServletRequest request);


    /**
     *
     * @param avatarFile
     * @param request
     * @return
     */
    int updateUserAvatar(MultipartFile avatarFile, HttpServletRequest request);

    /**
     *
     * @param tags
     * @param request
     * @return
     */
    int updateUserTags(List<String> tags, HttpServletRequest request);

}

