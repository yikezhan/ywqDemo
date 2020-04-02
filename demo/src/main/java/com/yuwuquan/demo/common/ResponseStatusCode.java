package com.yuwuquan.demo.common;

import com.yuwuquan.demo.exception.BaseExceptionCode;

public enum ResponseStatusCode {

    UNKNOWN_ERROR(BaseExceptionCode.UNKNOWN_ERROR),//-1
    SUCCESS(BaseExceptionCode.SUCCESS),//0
    FAILURE(BaseExceptionCode.FAILURE),//1
    USER_SECRET_CODE(10001,"用户或密码错误"),
    VERIFY_CODE_ERROR(10002,"手机验证码错误"),
    IMG_VERIFY_CODE_ERROR(10003,"图形验证码错误"),
    VERIFY_CODE_EXIST(10004,"手机验证码已存在，有效期内不能重复发送"),


    ;


    private Integer code;

    private String message;


    private ResponseStatusCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private ResponseStatusCode(BaseExceptionCode ex) {
        this.code = ex.getCode();
        this.message = ex.getMessage();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ResponseStatusCode obtainValuesOfCode(Integer code){
        ResponseStatusCode[] responseStatusCodes = ResponseStatusCode.values();
        for(ResponseStatusCode responseStatusCode : responseStatusCodes){
            if(responseStatusCode.code.equals(code)){
                return responseStatusCode;
            }
        }
        return null;
    }
}
