package com.fyp.qian.model.pojo.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class TagAddRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    private String parentTagName;

    private String tagName;

    private int available;
}
