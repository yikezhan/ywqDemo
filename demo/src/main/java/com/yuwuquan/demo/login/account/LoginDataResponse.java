package com.yuwuquan.demo.login.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.yuwuquan.demo.common.ResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@JsonInclude(Include.NON_NULL)
@ApiModel
public class LoginDataResponse extends ResponseDTO implements Serializable{
	
	private static final long serialVersionUID = -3035304306281690052L;
	
	@ApiModelProperty(value="用户ID")
	private String userId;
	
	@ApiModelProperty(value="用户名")
	private String userName;
	
	@ApiModelProperty(value="密码")
	private String password;
	
	@ApiModelProperty(value="验证码")
	private String verifyCode;
	
	@ApiModelProperty(value="是否登录成功",dataType="boolean",required=true)
	private boolean isLogin;

}
