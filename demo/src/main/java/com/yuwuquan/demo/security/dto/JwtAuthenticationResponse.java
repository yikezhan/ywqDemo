package com.yuwuquan.demo.security.dto;

import com.yuwuquan.demo.common.ResponseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class JwtAuthenticationResponse extends ResponseDTO {
	
	private static final long serialVersionUID = 1L;
	
	private String token="";
	
	public JwtAuthenticationResponse(String token) {
		if(token != null){
			this.token=token;
		}
	}

}
