package com.yuwuquan.demo.login.account;

import com.yuwuquan.demo.common.RequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
@ApiModel
public class LoginDataRequest extends RequestDTO implements Serializable{
	
	private static final long serialVersionUID = 4528027999680345971L;
	
	@NotNull
	@Valid
	@ApiModelProperty(value="登录请求数据",required=true)
	private LoginDataDTO reqDtos;
	
}
