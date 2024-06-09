package com.fyp.qian.model.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * tag insert waiting list table
 * @TableName place_tags_waiting
 */
@TableName(value ="place_tags_waiting")
@Data
public class PlaceTagsWaiting implements Serializable {
    /**
     * primary key
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * user id who creat this tag (-1 means this is created by system)
     */
    private Long userId;

    /**
     * tag for which osm element
     */
    private Long osmId;

    /**
     * osm element name
     */
    private String osmName;

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