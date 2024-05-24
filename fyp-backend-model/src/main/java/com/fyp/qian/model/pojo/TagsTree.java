package com.fyp.qian.model.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TagsTree implements Serializable {
    private String key;

    private String title;

    private boolean disabled;

    private boolean isLeaf;

    private List<TagsTree> children;

}
