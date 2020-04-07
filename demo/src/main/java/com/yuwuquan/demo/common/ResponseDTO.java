package com.yuwuquan.demo.common;

import com.yuwuquan.demo.exception.ApplicationException;
import com.yuwuquan.demo.exception.BaseExceptionCode;
import com.yuwuquan.demo.util.common.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 自定义返回类，返回实体装载在T模板中。
 * 1、构造函数私有化
 * 2、分为成功、失败（unknown异常，自定义异常，自定义返回码异常）
 * 3、模板T可传可不传，对外暴露8个方法
 * @param <T>
 */
@Setter
@Getter
@ApiModel
public class ResponseDTO<T> implements Serializable{

	private static final long serialVersionUID = -3404820470848004727L;

	@ApiModelProperty(value="返回实体类")
	private T t;

	@ApiModelProperty(value="响应消息",dataType="String",required=true,example="操作成功")
	private String message;
	
	@ApiModelProperty(value="错误消息",dataType="String",required=false,example="错误提示信息(可选)")
	private String errorMessage;
	
	@ApiModelProperty(value="状态码",dataType="Integer",required=true,example="100")
	private Integer responseCode;
	
	@ApiModelProperty(value="是否包含错误",dataType="Boolean",required=true,example="false")
	private boolean hasError;

	//##########################################成功####################################
	public static <T> ResponseDTO<T> success(T t){
		ResponseDTO responseDTO = new ResponseDTO<>(t);
		return responseDTO.successOp();
	}
	public static ResponseDTO success(){
		ResponseDTO responseDTO = new ResponseDTO();
		return responseDTO.successOp();
	}
	//##########################################失败####################################
	public static <T> ResponseDTO<T> systemFail(T t){
		ResponseDTO responseDTO = new ResponseDTO<>(t);
		return responseDTO.systemFailOp();
	}
	public static ResponseDTO systemFail(){
		ResponseDTO responseDTO = new ResponseDTO();
		return responseDTO.systemFailOp();
	}
	//##########################################异常####################################
	public static <T> ResponseDTO<T> systemFail(T t,ApplicationException ae){
		ResponseDTO responseDTO = new ResponseDTO<>(t);
		return responseDTO.systemFailOp(ae);
	}
	public static ResponseDTO systemFail(ApplicationException ae){
		ResponseDTO responseDTO = new ResponseDTO();
		return responseDTO.systemFailOp(ae);
	}
	//##########################################自定义返回码####################################
	public static <T> ResponseDTO<T> systemFail(T t,ResponseStatusCode responseStatusCode){
		ResponseDTO responseDTO = new ResponseDTO<>(t);
		return responseDTO.systemFailOp(responseStatusCode);
	}
	public static ResponseDTO systemFail(ResponseStatusCode responseStatusCode){
		ResponseDTO responseDTO = new ResponseDTO();
		return responseDTO.systemFailOp(responseStatusCode);
	}



	protected ResponseDTO(T t) {
		this.t = t;
	}

	protected ResponseDTO() {
	}

	private ResponseDTO successOp() {
		this.responseCode = ResponseStatusCode.SUCCESS.getCode();
		this.message = ResponseStatusCode.SUCCESS.getMessage();
		this.hasError = false;
		return this;
	}

	private ResponseDTO systemFailOp() {
		this.responseCode = ResponseStatusCode.UNKNOWN_ERROR.getCode();
		this.message = ResponseStatusCode.UNKNOWN_ERROR.getMessage();
		this.hasError = true;
		return this;
	}

	private ResponseDTO systemFailOp(ApplicationException ae) {
		this.responseCode = ae.getErrCode();
		this.message = ae.getMessage();
		this.errorMessage=ae.getErrorMsg();
		this.hasError = true;
		return this;
	}

	private ResponseDTO systemFailOp(ResponseStatusCode responseStatusCode){
		this.hasError = true;
		this.responseCode = responseStatusCode.getCode();
		this.message = responseStatusCode.getMessage();
		return this;
	}
}
