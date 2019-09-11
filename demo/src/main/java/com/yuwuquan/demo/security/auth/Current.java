package com.yuwuquan.demo.security.auth;

import com.yuwuquan.demo.session.Operator;
import com.yuwuquan.demo.session.SysContent;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Current {

	protected Company company;
	
	protected Dept dept;
	
	protected User user;
	
	/**
	 * 当前服务器时间信息
	 */
	protected DateInfo time;
	
	public static Current getCurrentUser(){
		
		Operator op = SysContent.getCurrentOperator();
		Current current = new Current();
		current.time = DateInfo.getDateInfo();
		if(op!=null){
			Company company = new Company();
			company.id = op.getCompanyId();
			company.name = op.getCompany();
			
			Dept dept = new Dept();
			dept.company = company;
			dept.name = op.getDepartment();
			dept.id = op.getDepartmentId();
			
			User user = new User();
			user.name = op.getUsername();
			user.id = op.getId();
			user.roleId = op.getRoleId();
			
			current.dept = dept;
			current.company = company;
			current.user = user;
			
		}
		return current;
	}
}
