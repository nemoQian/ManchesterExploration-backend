package com.fyp.qian.model.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user_group table
 * @TableName user_group_relation
 */
@TableName(value ="user_group_relation")
@Data
public class UserGroupRelation implements Serializable {
    /**
     * primary key
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * user id
     */
    private Long userId;

    /**
     * group id
     */
    private Long groupId;

    /**
     * user join time
     */
    private Date joinTime;

    /**
     * creation time of this relation
     */
    private Date createTime;

    /**
     * update time
     */
    private Date updateTime;

    /**
     * delete state
     */
    @TableLogic
    private Integer deleteState;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}