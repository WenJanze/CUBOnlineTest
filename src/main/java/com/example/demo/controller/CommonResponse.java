package com.example.demo.controller;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonResponse<T> {
    private String msg;
    private T data;
    private Boolean status;
    private int code;


    public static <T> CommonResponse<T> valueOf(boolean b, T data, ResponseEnum responseEnum) {
        CommonResponse<T> response = new CommonResponse<>();
        response.setStatus(b);
        response.setData(data);
        response.setCode(responseEnum.getHttpStatus().value());
        response.setMsg(responseEnum.getMsg());
        return response;
    }

    public static CommonResponse<Object> valueOf(boolean status, ResponseEnum errorCode, Object... args) {
        CommonResponse<Object> response = new CommonResponse<>();
        response.setStatus(status);
        response.setCode(errorCode.getHttpStatus().value());
        response.setMsg(errorCode.formatMessage(args));
        return response;
    }

}
