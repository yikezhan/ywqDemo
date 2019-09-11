package com.yuwuquan.demo.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuwuquan.demo.util.common.DateUtil;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User {

	@JsonIgnore
	private String id;

	private String userName;

	private String password;

	private List<String> roles;

	private String date;

	@JsonIgnore
	public Date getLastPasswordResetDate() {
		return DateUtil.parseDate(date);
	}


}
