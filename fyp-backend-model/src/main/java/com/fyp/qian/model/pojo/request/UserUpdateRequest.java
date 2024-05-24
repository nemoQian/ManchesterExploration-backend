package com.fyp.qian.model.pojo.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserUpdateRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    private String email;

    private String phone;

    private String nickname;

    private String gender;
}
