package com.fyp.qian.model.pojo.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class TagUserListResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    private String nickname;

    private int gender;

    private String updateDate;

    private List<String> tags;

    private Long userId;
}
