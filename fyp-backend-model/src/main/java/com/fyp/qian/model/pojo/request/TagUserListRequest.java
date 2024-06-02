package com.fyp.qian.model.pojo.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class TagUserListRequest extends PageRequest{

    private int current;

    private List<String> tags;

    private boolean strict;
}
