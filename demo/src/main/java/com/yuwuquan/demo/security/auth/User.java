package com.yuwuquan.demo.security.auth;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class User {
	
	protected Company company;
	
	protected Dept dept;
	
	protected Integer id;
	
	protected String name;
	
	protected Integer roleId;
	
}
