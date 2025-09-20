package com.example.demo.controller;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseEnum {
    SUCCESS(HttpStatus.OK,"成功"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "失敗"),
    HAVE_ID(HttpStatus.BAD_REQUEST,"必須ID" ),
    NOT_FIND(HttpStatus.BAD_REQUEST, "找不到資料" ),
    INSERT_NOT_HAVE_ID(HttpStatus.BAD_REQUEST,"新增不能輸入ID" ),
    EXIST_CURRENCY(HttpStatus.BAD_REQUEST, "已存在幣別資料" );
    private final HttpStatus httpStatus;
    private final String msg;


    ResponseEnum(HttpStatus httpStatus, String msg) {
        this.httpStatus = httpStatus;
        this.msg = msg;
    }

    public String formatMessage(Object... args) {
        if (args == null || args.length == 0) {
            return msg;
        }
        return String.format(msg.replace("{}", "%s"), args);
    }
}
