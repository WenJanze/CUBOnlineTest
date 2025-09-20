package com.example.demo.service.handler;

import com.example.demo.controller.CommonResponse;
import com.example.demo.controller.ResponseEnum;
import com.example.demo.service.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 處理自定義的業務異常
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonResponse<Object>> handleBusinessException(BusinessException e) {
        CommonResponse<Object> response = CommonResponse.valueOf(false, e.getErrorCode(), e.getArgs());
        log.error("業務異常，errorCode:{}，errorMsg:{}", e.getErrorCode(), response.getMsg());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(response);
    }

    /**
     * 參數驗證異常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<Object>> handleValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : "參數錯誤";
        CommonResponse<Object> response = CommonResponse.valueOf(false, ResponseEnum.BAD_REQUEST, message);
        log.error("參數驗證異常，field:{}，errorMsg:{}", fieldError != null ? fieldError.getField() : "unknown", response.getMsg());
        return ResponseEntity.status(ResponseEnum.BAD_REQUEST.getHttpStatus()).body(response);
    }

    /**
     * 處理參數綁定異常
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<CommonResponse<Object>> handleBindException(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : "參數錯誤";
        CommonResponse<Object> response = CommonResponse.valueOf(false, ResponseEnum.BAD_REQUEST, message);
        log.error("參數綁定異常，field:{}，errorMsg:{}", fieldError != null ? fieldError.getField() : "unknown", response.getMsg());
        return ResponseEntity.status(ResponseEnum.BAD_REQUEST.getHttpStatus()).body(response);
    }

    /**
     * 處理其他未知異常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Object>> handleException(Exception e) {
        CommonResponse<Object> response = CommonResponse.valueOf(false, ResponseEnum.BAD_REQUEST, "系統異常，請聯繫管理員");
        log.error("系統異常，exception:{}，errorMsg:{}", e.getClass().getSimpleName(), e.getMessage(), e);
        return ResponseEntity.status(ResponseEnum.BAD_REQUEST.getHttpStatus()).body(response);
    }
}
