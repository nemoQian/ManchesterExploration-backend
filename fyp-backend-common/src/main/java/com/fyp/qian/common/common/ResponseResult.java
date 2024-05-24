package com.fyp.qian.common.common;

import com.fyp.qian.model.enums.StateEnum;

public class ResponseResult {

    /**
     *
     * @param data
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>( StateEnum.SUCCESS.getCode(), StateEnum.SUCCESS.getMessage(), data );
    }

    /**
     *
     * @param stateEnum
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> error(StateEnum stateEnum){
        return new BaseResponse<>(stateEnum.getCode(), stateEnum.getMessage(), null);
    }
}
