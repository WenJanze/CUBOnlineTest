package com.example.demo.service.exception;

import com.example.demo.controller.ResponseEnum;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ResponseEnum errorCode;
    private final Object[] args;

    public BusinessException(ResponseEnum errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
        this.args = null;
    }

    public BusinessException(ResponseEnum errorCode, Object... args) {
        super(errorCode.formatMessage(args));
        this.errorCode = errorCode;
        this.args = args;
    }
}