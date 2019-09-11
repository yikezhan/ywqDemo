package com.yuwuquan.demo.login.account;

import com.yuwuquan.demo.common.ResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@ApiModel
public class UserResponse extends ResponseDTO implements Serializable{

	private static final long serialVersionUID = 8464927351457688069L;
	
	@Setter
	@Getter
	@ApiModelProperty(value="用户明细信息")
	private UserDetailDTO data;
	
}
