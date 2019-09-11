package com.yuwuquan.demo.login.account;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class User extends BaseTable implements  Serializable {
    
	private static final long serialVersionUID = -6329495335316138550L;

	private Integer id;
	
	private Byte status;

    private Integer employeeId;
    private String employeeName;
    private String employeeCode;
    
    private String password;
    private String username;

	private Integer roleId;
	private String roleName;
	
	private Integer companyId;
	private String company;
	
	private Integer departmentId;
	private String department;

	private String tel;
	private String email;
	private String wechat;
	private String qq;

    private String realname;
    
    private String phone;
    private Byte type;

    private Byte createFrom;
    private String newPassword;
    
    private Integer pageNum;
    private Integer pageSize;
	private String sortType;
	private String sortValue;
	/**
	 * 岗位ID
	 */
	private String fkPost;
	/**
	 * 岗位名称
	 */
	private String postName;

	/**
	 * 部门code
	 */
	private String departmentCode;
	/**
	 * 在职(1在职,其他离职)
	 */
	private Byte isWorking;
}