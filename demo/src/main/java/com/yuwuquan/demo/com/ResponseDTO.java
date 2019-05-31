package com.yuwuquan.demo.com;

import com.yuwuquan.demo.exception.ApplicationException;
import com.yuwuquan.demo.exception.BaseExceptionCode;
import com.yuwuquan.demo.util.StringUtil;
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
	private String responseCode;
	
	@ApiModelProperty(value="是否包含错误",dataType="Boolean",required=true,example="false")
	private boolean hasError;

	
	public void success(String msg) {
		this.responseCode = StringUtil.valueOf(BaseExceptionCode.SUCCESS.getCode());
//		this.message = ResponseStatusCode.SUCCESS.getMessage();
		this.message = msg;
		this.hasError = false;
	}
	
	public void systemFail() {
		this.responseCode = StringUtil.valueOf(BaseExceptionCode.UNKNOWN_ERROR.getCode());
		this.message = BaseExceptionCode.UNKNOWN_ERROR.getMessage();
		this.hasError = true;
	}
	
	public void systemFail(ApplicationException ae) {
		this.responseCode = StringUtil.valueOf(ae.getErrCode());
		this.message = ae.getMessage();
		this.errorMessage=ae.getErrorMsg();
		this.hasError = true;
	}
	
	public void systemFail(String code, String msg, String errmsg) {
		this.hasError = true;
		this.responseCode = code;
		this.message = msg;
		this.errorMessage = errmsg;
		/*if( e != null ){
			this.responseCode =Integer.toString(e.getErrCode());
			this.message = e.getErrorMsg();
			this.errorMessage = e.getErrorMsg();
		}else{
			this.responseCode = StringUtil.valueOf(ResponseStatusCode.UNKNOWN_ERROR.getCode());
			this.message = msg;
			this.errorMessage = msg;
		}*/
	}
}
