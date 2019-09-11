package com.yuwuquan.demo.login.account;

import com.yuwuquan.demo.common.ResponseDTO;
import com.yuwuquan.demo.session.Operator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel
public class UserPermissionResponse extends ResponseDTO implements Serializable {
	
	private static final long serialVersionUID = 8304811011316944808L;
	@ApiModelProperty(value="当前用户",required=true)
	private Operator user;

}
