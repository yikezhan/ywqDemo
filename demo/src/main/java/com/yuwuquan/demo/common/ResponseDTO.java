package com.yuwuquan.demo.common;

import com.yuwuquan.demo.exception.ApplicationException;
import com.yuwuquan.demo.exception.BaseExceptionCode;
import com.yuwuquan.demo.util.common.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@ApiModel
public class ResponseDTO implements Serializable{

	private static final long serialVersionUID = -3404820470848004727L;

	@ApiModelProperty(value="响应消息",dataType="String",required=true,example="操作成功")
	private String message;
	
	@ApiModelProperty(value="错误消息",dataType="String",required=false,example="错误提示信息(可选)")
	private String errorMessage;
	
	@ApiModelProperty(value="状态码",dataType="Integer",required=true,example="100")
	private Integer responseCode;
	
	@ApiModelProperty(value="是否包含错误",dataType="Boolean",required=true,example="false")
	private boolean hasError;

	public ResponseDTO success() {
		this.responseCode = ResponseStatusCode.SUCCESS.getCode();
		this.message = ResponseStatusCode.SUCCESS.getMessage();
		this.hasError = false;
		return this;
	}
	
	public ResponseDTO systemFail() {
		this.responseCode = ResponseStatusCode.UNKNOWN_ERROR.getCode();
		this.message = ResponseStatusCode.UNKNOWN_ERROR.getMessage();
		this.hasError = true;
		return this;
	}
	
	public ResponseDTO systemFail(ApplicationException ae) {
		this.responseCode = ae.getErrCode();
		this.message = ae.getMessage();
		this.errorMessage=ae.getErrorMsg();
		this.hasError = true;
		return this;
	}

	public ResponseDTO systemFail(ResponseStatusCode responseStatusCode){
		this.hasError = true;
		this.responseCode = responseStatusCode.getCode();
		this.message = responseStatusCode.getMessage();
		return this;
	}
}
