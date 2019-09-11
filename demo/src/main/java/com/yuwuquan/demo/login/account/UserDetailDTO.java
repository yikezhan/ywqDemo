package com.yuwuquan.demo.login.account;

import com.yuwuquan.demo.common.RequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=true)
@ApiModel
public class UserDetailDTO extends RequestDTO implements Serializable {

	private static final long serialVersionUID = -3250342538693895991L;

	@ApiModelProperty(value="id", dataType="Integer")
	private String id;

	@ApiModelProperty(value="用户名", dataType="String")
	private String username;

	@ApiModelProperty(value="工号", dataType="String")
	private String employeeCode;

	@ApiModelProperty(value="姓名", dataType="String")
	private String realname;

	@ApiModelProperty(value="公司", dataType="String")
	private String company;
	
	@ApiModelProperty(value="公司ID", dataType="String")
	private Integer companyId;

	@ApiModelProperty(value="部门", dataType="String")
	private String department;

	@ApiModelProperty(value="电话", dataType="String")
	private String tel;

	@ApiModelProperty(value="邮箱", dataType="String")
	private String email;

	@ApiModelProperty(value="微信", dataType="String")
	private String wechat;

	@ApiModelProperty(value="qq", dataType="String")
	private String qq;

	@ApiModelProperty(value="语言", dataType="String")
	private String language;

	@ApiModelProperty(value="密码", dataType="String")
	private String password;

	@ApiModelProperty(value="岗位Id", dataType="String")
	private String postId;

	@ApiModelProperty(value="岗位名称", dataType="String")
	private String postName;

	@ApiModelProperty(value="二维码", dataType="String")
	private String qrCode;

	@ApiModelProperty(value="是否供应商", dataType="boolean")
	private boolean isSupplier;

}
