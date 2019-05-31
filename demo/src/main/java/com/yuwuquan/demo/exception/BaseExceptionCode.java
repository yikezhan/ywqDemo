package com.yuwuquan.demo.exception;

public enum BaseExceptionCode {
	
	SUCCESS(0,"{sys.success}","操作成功"),
	FAILURE(1,"{sys.failure}","操作失败"),
	UNKNOWN_ERROR(-1,"{sys.unknow}","未知错误"),
	SYSTEM_ERROR(-100,"{sys.error}","系统级错误"),	
	INVALID_REQUEST(1001,"{sys.illegal.parameter}","非法参数"),
	FAIL_TO_VALIDATE(1002,"{sys.validation.failure}","参数验证失败"),
	DATA_ACCESS_EXCEPTION(1003,"{sys.database.exception}","数据库异常"),
	VAILDATOR_SETTING_ERROR(1004,"{sys.vaildator.setting.error}","校验规则错误"),
	NO_AUTHORIZATION(1011,"{sys.auth.user.not.exists}","没有登陆,请重新登陆!"),
	ARGS_VALIDATE_ERROR(1012, "{sys.args.validate.error}", "参数校验失败"),
	NO_WIDE_ACCESS(1013,"{sys.auth.not.accessable}","禁止访问!");
	
	
	private Integer code;
	
	private String message;
	
	private String langCode;

	private BaseExceptionCode(Integer code, String langCode, String message){
		this.code = code;
		this.message = message;
		this.langCode = langCode;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	public String getLangCode() {
		return langCode;
	}

}
