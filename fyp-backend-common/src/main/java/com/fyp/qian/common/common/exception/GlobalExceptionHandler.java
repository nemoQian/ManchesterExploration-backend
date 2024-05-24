package com.fyp.qian.common.common.exception;

import com.fyp.qian.common.common.BaseResponse;
import com.fyp.qian.model.enums.StateEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e) {
        return new BaseResponse<>(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e) {
        log.error("Runtime Exception",e);
        return new BaseResponse<>(StateEnum.SYSTEM_ERROR.getCode(), StateEnum.SYSTEM_ERROR.getMessage(), e.getMessage());
    }
}
