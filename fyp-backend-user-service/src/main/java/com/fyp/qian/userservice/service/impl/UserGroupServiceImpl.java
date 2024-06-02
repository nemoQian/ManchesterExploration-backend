package com.fyp.qian.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.qian.common.common.exception.BusinessException;
import com.fyp.qian.model.enums.StateEnum;
import com.fyp.qian.model.pojo.User;
import com.fyp.qian.model.pojo.UserGroup;
import com.fyp.qian.model.pojo.UserGroupRelation;
import com.fyp.qian.model.pojo.request.GroupSearchRequest;
import com.fyp.qian.userservice.service.UserGroupRelationService;
import com.fyp.qian.userservice.service.UserGroupService;
import com.fyp.qian.userservice.mapper.UserGroupMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.fyp.qian.common.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author Yihan Qian
* @description 针对表【user_group(group table)】的数据库操作Service实现
* @createDate 2024-06-02 15:13:11
*/
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup>
    implements UserGroupService{

    @Resource
    private UserGroupRelationService userGroupRelationService;

    @Override
    public Long addGroup(UserGroup userGroup, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (currentUser == null || currentUser.getDeleteState() == 1) {
            throw new BusinessException(StateEnum.NO_USER_ERROR);
        }
        if (userGroup == null) {
            throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR);
        }
        QueryWrapper<UserGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_name", userGroup.getGroupName());
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(StateEnum.DUPLICATE_GROUP_ERROR);
        }

        userGroup.setGroupCreateUserId(currentUser.getId());

        boolean result = this.save(userGroup);
        if (!result) {
            throw new BusinessException(StateEnum.DUPLICATE_GROUP_ERROR);
        }

        UserGroupRelation userGroupRelation = new UserGroupRelation();
        userGroupRelation.setGroupId(userGroup.getId());
        userGroupRelation.setUserId(userGroup.getGroupCreateUserId());
        userGroupRelation.setJoinTime(new Date());

        result = userGroupRelationService.save(userGroupRelation);
        if (!result) {
            throw new BusinessException(StateEnum.DUPLICATE_GROUP_ERROR);
        }
        return userGroup.getId();
    }

    @Override
    public Page<UserGroup> listGroup(GroupSearchRequest groupSearchRequest) {
        if(groupSearchRequest == null){
            throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR);
        }

        UserGroup group = new UserGroup();
        group.setGroupCreateUserId(groupSearchRequest.getGroupCreateUserId());
        group.setGroupName(groupSearchRequest.getGroupName());
        group.setGroupDescription(groupSearchRequest.getGroupDescription());
        group.setGroupVisibility(groupSearchRequest.getGroupVisibility());

        Page page = new Page<>(groupSearchRequest.getPageNum(), groupSearchRequest.getPageSize());
        QueryWrapper<UserGroup> queryWrapper = new QueryWrapper<>();
        Page<UserGroup> userGroupPage = this.page(page, queryWrapper);
        return userGroupPage;
    }
}




