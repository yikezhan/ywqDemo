package com.yuwuquan.demo.security.auth;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Dept {

	/**
	 * 部门ID 
	 */
	protected Integer id;
	
	protected String name;
	
	/**
	 * 部门所属公司
	 */
	protected Company company;
	
	/**
	 * 所有上层部门 
	 */
	protected String[] allParentDept;
	
}
