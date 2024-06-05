package com.fyp.qian.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * tag table
 * @TableName place_tags
 */
@TableName(value ="place_tags")
@Data
public class PlaceTags implements Serializable {
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
     * tag name
     */
    private String tagName;

    /**
     * parent id of this tag in this tag family (-1 means this is the parent tag)
     */
    private Long tagParentId;

    /**
     * 0 - global, 1 - group, 2 - private
     */
    private Integer tagShownStatus;

    /**
     * who can use this tags
     */
    private Long tagBelongs;

    /**
     * tags approval state
     */
    private Integer approvalState;

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
    private Integer deleteState;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}