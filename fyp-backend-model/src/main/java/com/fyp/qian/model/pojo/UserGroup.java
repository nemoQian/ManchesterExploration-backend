package com.fyp.qian.model.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * group table
 * @TableName user_group
 */
@TableName(value ="user_group")
@Data
public class UserGroup implements Serializable {
    /**
     * primary key
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * group name
     */
    private String groupName;

    /**
     * group description
     */
    private String groupDescription;

    /**
     * user id who create this group
     */
    private Long groupCreateUserId;

    /**
     * group visibility 0 - public, 1 - private
     */
    private Integer groupVisibility;

    /**
     * creation time of this group
     */
    private Date createTime;

    /**
     * group update time
     */
    private Date updateTime;

    /**
     * group delete state
     */
    @TableLogic
    private Integer deleteState;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}