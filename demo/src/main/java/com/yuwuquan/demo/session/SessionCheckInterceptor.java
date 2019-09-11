package com.yuwuquan.demo.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lenovo
 *
 */
@Slf4j
public class SessionCheckInterceptor extends HandlerInterceptorAdapter {

	private String contextRoot;
	
	public SessionCheckInterceptor(String contextRoot) {
		this.contextRoot = contextRoot;
	}
	
	private static final String VCODE_IMG_NAME="vcode.jpg";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getRequestURL().toString();
		log.debug(contextRoot+"-->"+url);
		if(SysContent.getCurrentOperator().getUsername().equals(Operator.EMPTY.getUsername())){
			if(!url.endsWith(VCODE_IMG_NAME)){
//				response.setStatus(401);
//				response.sendRedirect("http://scm.tc.com");
//				return false;
			}
		}
		return true;
	}
}
