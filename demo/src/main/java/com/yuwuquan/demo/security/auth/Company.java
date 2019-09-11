package com.yuwuquan.demo.security.auth;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Company {

	protected Integer id;
	
	protected String name;
	
	/**
	 * 所有上层公司 
	 */
	protected String[] allParentCompany;
}
