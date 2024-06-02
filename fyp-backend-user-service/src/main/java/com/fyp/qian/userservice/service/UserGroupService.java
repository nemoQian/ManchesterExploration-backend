package com.fyp.qian.userservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.qian.model.pojo.UserGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fyp.qian.model.pojo.request.GroupSearchRequest;
import jakarta.servlet.http.HttpServletRequest;

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
     * @return
     */
    Page<UserGroup> listGroup(GroupSearchRequest groupSearchRequest);
}
