package com.yuwuquan.demo.security.dto;

import lombok.Data;

@Data
public class JwtAuthenticationRequest {
	
	private String userName;
	
	private String password;

}
