package com.fyp.qian.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * user_table
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * column id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * login username
     */
    private String username;

    /**
     * login password
     */
    private String userPassword;

    /**
     * user nickname
     */
    private String nickname;

    /**
     * user gender
     */
    private Integer gender;

    /**
     * user avatar url
     */
    private String avatarUrl;

    /**
     * user phone
     */
    private String phone;

    /**
     * user email
     */
    private String email;

    /**
     * user account status
     */
    private Integer accountStatus;

    /**
     * user account create time
     */
    private Date createTime;

    /**
     * user account update time
     */
    private Date updateTime;

    /**
     * user account delete state
     */
    @TableLogic
    private Integer deleteState;

    /**
     * user role 0 - user, 1 - admin
     */
    private Integer userRole;

    /**
     * user tags
     */
    private String tags;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}