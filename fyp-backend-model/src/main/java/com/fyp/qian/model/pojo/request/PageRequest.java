package com.fyp.qian.model.pojo.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class PageRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    protected int pageSize;

    protected int pageNum;
}
