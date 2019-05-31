package com.yuwuquan.demo.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 * @author ywq
 */
public class StringUtil extends StringUtils{
    public static String valueOf(Object obj){
    	return obj == null? null : String.valueOf(obj);
    }
    public static String valueOf(Object obj,String defaultValue){
    	return obj == null? defaultValue : String.valueOf(obj);
    }
}

