package com.fyp.qian.userservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.qian.model.pojo.UserGroupRelation;
import com.fyp.qian.userservice.service.UserGroupRelationService;
import com.fyp.qian.userservice.mapper.UserGroupRelationMapper;
import org.springframework.stereotype.Service;

/**
* @author Yihan Qian
* @description 针对表【user_group_relation(user_group table)】的数据库操作Service实现
* @createDate 2024-06-02 15:12:25
*/
@Service
public class UserGroupRelationServiceImpl extends ServiceImpl<UserGroupRelationMapper, UserGroupRelation>
    implements UserGroupRelationService{

}




