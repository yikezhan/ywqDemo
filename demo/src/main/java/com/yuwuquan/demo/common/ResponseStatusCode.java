package com.yuwuquan.demo.common;

import com.yuwuquan.demo.exception.BaseExceptionCode;

public enum ResponseStatusCode {

    SUCCESS(BaseExceptionCode.SUCCESS),
	FAILURE(BaseExceptionCode.FAILURE),
	UNKNOWN_ERROR(BaseExceptionCode.UNKNOWN_ERROR),
	SYSTEM_ERROR(BaseExceptionCode.SYSTEM_ERROR),	
	INVALID_REQUEST(BaseExceptionCode.INVALID_REQUEST),
	FAIL_TO_VALIDATE(BaseExceptionCode.FAIL_TO_VALIDATE),
	DATA_ACCESS_EXCEPTION(BaseExceptionCode.DATA_ACCESS_EXCEPTION),
	VAILDATOR_SETTING_ERROR(BaseExceptionCode.VAILDATOR_SETTING_ERROR),
    VERIFY_CODE_ERROR(1009,"验证码错误"),
    LOGGIN_SUCESS(1012,"登录成功"),
    VALIDATE_PWD_ERROE(1008,"旧密码错误"),
    USER_NOT_EXISTS(1010,"用户或密码错误"),
    NO_AUTHORIZATION(BaseExceptionCode.NO_AUTHORIZATION),
	EMPLOYEEID_NULL(1013,"员工信息为空"),
	STATUS_NULL(1014,"状态不能为空"),
    EMPLOYEE_ISEXIST_ISNOJOB(1021,"所属员工不存在或不在职"),


    //权限,
    PERMISSION_USERROLE_ROLE_NOTNULL(103009,"角色id不能为空"),
    PERMISSION_USERNAME_REPEAT(1030110,"账号用户名不能重复!"),
    PERMISSION_USERNAME_NOTEXISTS(1030111,"账号不存在!"),

    PERMISSION_USERPOST_NOTNULL(103012,"用户岗位不能为空!"),

    PERMISSION_ROLE_NOEXISTS(103009,"角色不存在"),
    PERMISSION_USER_NOEXISTS(103010,"用户不存在"),

    PERMISSION_ROLE_IDENTICAL(103011,"同一个公司下不能有两个名称相同的角色");


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
