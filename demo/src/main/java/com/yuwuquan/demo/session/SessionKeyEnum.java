package com.yuwuquan.demo.session;

public enum SessionKeyEnum {

	VCODENAME("sysVerCode","分页默认每页20条数据"),
	FIRSTLOGINFAILED("isFirstLogin","是否第一次登陆"),
	USERPERMISSION("userPermissionStr","用户权限"),
	CURRENTUSEROPERATOR("currentUserOperator","当前登陆用户"),
	MYSIGNINCODENAME("mySignInCodeName","前台用户注册/登陆验证码");
	
	private String code;
	
	private String message;
	
	private SessionKeyEnum(String code, String message){
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
