package com.yuwuquan.demo.security.auth;

import com.yuwuquan.demo.util.common.DateUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class DateInfo {
	
	String date;
	String datetime;
	
	public static DateInfo getDateInfo(){
		DateInfo info = new DateInfo();
		Date date = DateUtil.getDate();
		info.date = DateUtil.get4yMdNoDash(date);
		info.datetime = DateUtil.get4yMdHmsNoDash(date);
		return info;
	}
}
