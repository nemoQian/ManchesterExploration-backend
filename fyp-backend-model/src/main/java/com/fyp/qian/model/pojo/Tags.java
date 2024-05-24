package com.fyp.qian.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * tag table
 * @TableName tags
 */
@TableName(value ="tags")
@Data
public class Tags implements Serializable {
    /**
     * primary key
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * user id who creat this tag
     */
    private Long userId;

    /**
     * tag name
     */
    private String tagName;

    /**
     * tag id list of this tag family
     */
    private String tagNode;

    /**
     * parent id of this tag in this tag family (-1 means this is the parent tag)
     */
    private Long tagParentId;

    /**
     * 0 - unavailable & 1 - available
     */
    private Integer tagAvailable;

    /**
     * creation time of this tag
     */
    private Date createTime;

    /**
     * tag update time
     */
    private Date updateTime;

    /**
     * tag delete state
     */
    @TableLogic
    private Integer deleteState;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}