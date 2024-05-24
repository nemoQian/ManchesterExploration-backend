package com.fyp.qian.common.common.exception;

import com.fyp.qian.model.enums.StateEnum;

/**
 * @author Yihan Qian
 */
public class BusinessException extends RuntimeException {

    private final int code;

    private String description;

    public BusinessException(StateEnum stateEnum) {
        super(stateEnum.getMessage());
        this.code = stateEnum.getCode();
    }

    public BusinessException(StateEnum stateEnum, String description) {
        super(stateEnum.getMessage());
        this.code = stateEnum.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
