package com.fyp.qian.common.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Yihan Qian
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private String message;

    private T data;

    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
