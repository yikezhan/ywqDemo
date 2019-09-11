package com.yuwuquan.demo.security.dto;

import com.yuwuquan.demo.common.ResponseDTO;
import com.yuwuquan.demo.security.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class JwtUserRegisterResponse extends ResponseDTO {
	
	private static final long serialVersionUID = 1L;
	
	private User data;

}
