package com.fyp.qian.userservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.qian.model.pojo.UserGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fyp.qian.model.pojo.request.GroupSearchRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author Yihan Qian
* @description 针对表【user_group(group table)】的数据库操作Service
* @createDate 2024-06-02 15:13:11
*/
public interface UserGroupService extends IService<UserGroup> {

    /**
     *
     * @param userGroup
     * @param request
     * @return
     */
    Long addGroup(UserGroup userGroup, HttpServletRequest request);

    /**
     *
     * @param groupSearchRequest
     * @param request
     * @return
     */
    Page<UserGroup> listUserGroup(GroupSearchRequest groupSearchRequest, HttpServletRequest request);

    /**
     *
     * @param groupSearchRequest
     * @param request
     * @return
     */
    Page<UserGroup> searchGroup(GroupSearchRequest groupSearchRequest, HttpServletRequest request);

    /**
     *
     * @param groupSearchRequest
     * @param request
     * @return
     */
    Long joinGroup(GroupSearchRequest groupSearchRequest, HttpServletRequest request);
}
