package com.yuwuquan.demo.login.controller;

import com.yuwuquan.demo.session.Operator;
import com.yuwuquan.demo.session.SessionKeyEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Slf4j
@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class LoginOperation {
	
	public final static String TOKEN_NAME="AccessToken";

	//获取客户端ip地址
	public static String getClientIP(HttpServletRequest request) {
	    String ip = request.getHeader("x-forwarded-for");   
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	        ip = request.getHeader("Proxy-Client-IP");   
	    }   
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	        ip = request.getHeader("WL-Proxy-Client-IP");   
	  
	    }   
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	        ip = request.getRemoteAddr();   
	    }   
	    return ip;   
	}
	
	public static  void loginFailed(HttpServletResponse response, HttpSession session){
		
		session.setAttribute(SessionKeyEnum.FIRSTLOGINFAILED.getCode(), Boolean.TRUE.toString());
		
		Cookie logFail = new Cookie("vcode", Boolean.TRUE.toString());
		logFail.setPath("/");
		
		response.addCookie(logFail);
	}
	
	public static  void afterLoginSuccess(HttpServletRequest request, HttpServletResponse response, HttpSession session, Operator operator){
		String accessToken = UUID.randomUUID().toString();
		Cookie cookieAuth = new Cookie(TOKEN_NAME, accessToken);
		cookieAuth.setPath("/");
		cookieAuth.setMaxAge(7200);
		
		response.addCookie(cookieAuth);
		log.info("Operator:"+operator.toString());
		operator.setAccessToken(accessToken);
		session.setAttribute(SessionKeyEnum.CURRENTUSEROPERATOR.getCode(), operator);
		session.removeAttribute(SessionKeyEnum.FIRSTLOGINFAILED.getCode());

	}

}
