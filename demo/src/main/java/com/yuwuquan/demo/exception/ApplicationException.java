package com.yuwuquan.demo.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 8747065382288228250L;

	@Getter
	private String errorMsg;
	
	@Getter
	private int errCode;
	
	@Getter
	private Object[] args;
	
	public ApplicationException(String errorMsg, int errorCode){
		super(errorMsg);
		this.errCode=errorCode;
		this.errorMsg=errorMsg;
	}
	
	public ApplicationException(String errorMsg, int errorCode, Object... args){
		super(errorMsg);
		this.errCode=errorCode;
		this.errorMsg=errorMsg;
		this.args = args;
	}

}
