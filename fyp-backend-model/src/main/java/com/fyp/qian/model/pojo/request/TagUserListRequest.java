package com.fyp.qian.model.pojo.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class TagUserListRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    private int current;

    private int pageSize;

    private List<String> tags;

    private boolean strict;
}
