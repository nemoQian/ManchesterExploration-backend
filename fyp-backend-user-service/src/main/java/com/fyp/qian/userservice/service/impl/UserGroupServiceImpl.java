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
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    public Page<UserGroup> listUserGroup(GroupSearchRequest groupSearchRequest, HttpServletRequest request) {
        if(groupSearchRequest == null){
            throw new BusinessException(StateEnum.PARAMS_EMPTY_ERROR);
        }

        User currentUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (currentUser == null || currentUser.getDeleteState() == 1) {
            throw new BusinessException(StateEnum.NO_USER_ERROR);
        }
        List<Long> groupIds = getGroupIdList(currentUser.getId());

        Page<UserGroup> page = new Page<>(groupSearchRequest.getCurrent(), groupSearchRequest.getPageSize());
        QueryWrapper<UserGroup> queryWrapper = new QueryWrapper<>();

        if (!groupIds.isEmpty()) {
            queryWrapper.in("id", groupIds);
        }

        Page<UserGroup> userGroupPage = this.page(page, queryWrapper);


        for (UserGroup userGroup : userGroupPage.getRecords()) {
            if(Objects.equals(userGroup.getGroupCreateUserId(), currentUser.getId())){
                userGroup.setGroupCreateUserId(-1L);
            }
        }
        return userGroupPage;
    }

    @Override
    public Page<UserGroup> searchGroup(GroupSearchRequest groupSearchRequest, HttpServletRequest request) {
        QueryWrapper<UserGroup> queryWrapper = new QueryWrapper<>();
        if(groupSearchRequest != null){
            String groupName = groupSearchRequest.getGroupName();
            if (StringUtils.isNotBlank(groupName)) {
                queryWrapper.apply("LOWER(group_name) LIKE LOWER({0})", "%" + groupName + "%");
            }
            Long groupCreateUserId = groupSearchRequest.getGroupCreateUserId();
            if(groupCreateUserId != null){
                queryWrapper.eq("group_create_user_id", groupCreateUserId);
            }
            queryWrapper.eq("group_visibility", 0);
        }
        User currentUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (currentUser == null || currentUser.getDeleteState() == 1) {
            throw new BusinessException(StateEnum.NO_USER_ERROR);
        }

        List<Long> groupIds = getGroupIdList(currentUser.getId());

        Page<UserGroup> page = new Page<>(groupSearchRequest.getCurrent(), groupSearchRequest.getPageSize());

        if (!groupIds.isEmpty()) {
            queryWrapper.notIn("id", groupIds);
        }

        Page<UserGroup> userGroupPage = this.page(page, queryWrapper);

        return userGroupPage;
    }

    @Override
    public Long joinGroup(GroupSearchRequest groupSearchRequest, HttpServletRequest request) {
        long groupId = groupSearchRequest.getId();
        User currentUser = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (currentUser == null || currentUser.getDeleteState() == 1) {
            throw new BusinessException(StateEnum.NO_USER_ERROR);
        }
        List<Long> groupIds = getGroupIdList(currentUser.getId());
        if(!groupIds.isEmpty()){
            if(groupIds.contains(groupId)){
                throw new BusinessException(StateEnum.DUPLICATE_GROUP_JOIN_ERROR);
            }
        }

        UserGroupRelation userGroupRelation = new UserGroupRelation();
        userGroupRelation.setGroupId(groupId);
        userGroupRelation.setUserId(currentUser.getId());
        userGroupRelation.setJoinTime(new Date());

        boolean result = userGroupRelationService.save(userGroupRelation);
        if (!result) {
            throw new BusinessException(StateEnum.DUPLICATE_GROUP_ERROR);
        }
        return userGroupRelation.getId();
    }

    private List<Long> getGroupIdList(Long userId) {
        List<Long> groupIdList = new ArrayList<>();
        QueryWrapper<UserGroupRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserGroupRelation> userGroupRelations = userGroupRelationService.list(queryWrapper);
        for (UserGroupRelation userGroupRelation : userGroupRelations) {
            groupIdList.add(userGroupRelation.getGroupId());
        }
        return groupIdList;
    }
}




