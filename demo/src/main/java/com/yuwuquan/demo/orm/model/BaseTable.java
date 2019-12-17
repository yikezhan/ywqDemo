package com.yuwuquan.demo.orm.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BaseTable {
	
	public static final Integer VAILD=1;
	public static final Integer INVAILD=0;
	
	public static Integer getVaildCode(Boolean valid){
		if(valid==null){
			return null;
		}else{
			return valid.booleanValue()?VAILD:INVAILD; 
		}
	}
	
	public static Boolean getVaildCode(Integer valid){
		if(valid==null){
			return null;
		}else{
			return valid.intValue()==0?false:true; 
		}
	}
	
	private Timestamp createTime;

	private Timestamp modifyTime;

	private Integer fkUserCreate;

	private Integer fkUserModify;
	
	private Integer isValid;
	
	public void setValid(Boolean valid){
		if(valid!=null){
			if(valid){
				this.isValid=1;
			}else{
				this.isValid=0;
			}
		}
	}

}
