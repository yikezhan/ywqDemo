package com.yuwuquan.demo.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

public class PwdUtils {
	
	//35是因为数组是从0开始的，26个字母+10个 数字
	private static final int  maxNum = 36;
	
	private static char[] str = {
		'a','b','c','d','e','f','g','h','i','j','k','l','m',
		'n','o','p','q','r','s','t','u','v','w','x','y','z', 
		'0','1','2','3','4','5','6','7','8','9'};
	
	public static String genRandomNum(){
		int i;  //生成的随机数
		int count = 0; //生成的密码的长度
		
		StringBuilder pwd = new StringBuilder("");
		Random r = new Random();
		while(count < 8){
			//生成随机数，取绝对值，防止 生成负数，
			i = Math.abs(r.nextInt(maxNum));  //生成的数最大为36-1
			if (i >= 8 && i < str.length) {
				pwd.append(str[i]);
				count ++;
			}
		}
		return pwd.toString();
	}

	public static String encryptPassword(String userName,String password){
		String data = userName+password;
		return DigestUtils.md5Hex(data);
	}
	
}
