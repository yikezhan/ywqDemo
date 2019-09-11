package com.yuwuquan.demo.session;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lenovo
 *
 */
@Data
public class Operator implements Serializable {
	
	private static final long serialVersionUID = -7884488850361068157L;
	
	public final static Operator EMPTY = new Operator();
	
	static{
		EMPTY.setId(-1);
		EMPTY.setUsername("unkonw");
		EMPTY.setRealname("未知用户");
		EMPTY.setRemoteIp("127.0.0.1");
		EMPTY.setCompanyId(-1);
		EMPTY.setCompany("未知公司");
		EMPTY.setDepartmentId(-1);
		EMPTY.setDepartment("未知部门");
		EMPTY.setAccessToken("-1");
		EMPTY.setDepartmentCode("-1");
	}
	
	public static Operator getEmpty(){
		Operator empty = new Operator();
		empty.setId(-1);
		empty.setUsername("unkonw");
		empty.setRealname("未知用户");
		empty.setRemoteIp("127.0.0.1");
		empty.setCompanyId(-1);
		empty.setCompany("未知公司");
		empty.setDepartmentId(-1);
		empty.setDepartment("未知部门");
		empty.setAccessToken("-1");
		empty.setDepartmentCode("-1");
		return empty;
	}

	private String remoteIp;
	
	private String accessToken;
	
	private Integer id;
	
	private Byte status;

    private Integer employeeId;
    private String employeeName;
    
    private String password;
    private String username;

	private Integer roleId;
	
	private Integer companyId;
	private String company;
	
	private Integer departmentId;
	private String department;

	
    private String realname;
    
    private Byte type;

    private Byte createFrom;

	/**
	 * 部门code
	 */
	private String departmentCode;
    
}
