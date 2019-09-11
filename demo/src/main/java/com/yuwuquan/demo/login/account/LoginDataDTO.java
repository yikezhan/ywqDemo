package com.yuwuquan.demo.login.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Setter
@Getter
@ApiModel
public class LoginDataDTO implements Serializable{

	private static final long serialVersionUID = 3550501094325187673L;
	
	@NotEmpty(message = "用户名不能为空")
	@ApiModelProperty(value="用户名",required=true,example="admin")
	private String userName;
	
	@NotEmpty(message = "密码不能为空")
	@ApiModelProperty(value="用户密码",required=true,example="admin")
	private String password;
	
	@ApiModelProperty(value="验证码",example="1323")
	private String verifyCode;
}
