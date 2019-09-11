package com.yuwuquan.demo.session;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;
import com.yuwuquan.demo.security.auth.AuthContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SysContent {
	
	private static ThreadLocal<HttpServletRequest> REQUEST_LOCAL = new ThreadLocal<>();
    private static ThreadLocal<HttpServletResponse> RESPONSE_LOCAL = new ThreadLocal<>();
	private static ThreadLocal<Operator> USER_LOCAL = new ThreadLocal<>();
    /**
     * 数据权限上下文 ,这个对象比较奇怪，感觉所有线程都一样，不用清理
     */
    private static ThreadLocal<AuthContext> AUTHCONTEXT = new ThreadLocal<>();
    
    public static HttpServletRequest getRequest() {
        return REQUEST_LOCAL.get();  
    }
    public static void setOperatorFromDubbo(Operator op) {
    	USER_LOCAL.set(op);
    	//并找不到合适的清理地方，放到http拦截器清理了
    }
    public static AuthContext getAuthContext(){
    	AuthContext ctx = AUTHCONTEXT.get();
    	if(ctx==null){
    		ctx = new AuthContext();
    		AUTHCONTEXT.set(ctx);
    	}
    	return ctx;
    }
  
    public static void setRequest(HttpServletRequest request) {
    	HttpSession session  = request.getSession();
    	if(session!=null){
    		Object o = session.getAttribute(SessionKeyEnum.CURRENTUSEROPERATOR.getCode());
    		if(o != null && o instanceof Operator){
    			RpcContext.getContext().setAttachment(SessionKeyEnum.CURRENTUSEROPERATOR.getCode(), JSON.toJSONString(o));
    		}
    	}
        REQUEST_LOCAL.set(request);  
    }  
  
    public static HttpServletResponse getResponse() {
        return RESPONSE_LOCAL.get();  
    }  
  
    public static void setResponse(HttpServletResponse response) {
        RESPONSE_LOCAL.set(response);  
    }
    
    public static void cleanThreadHttp() {
    	RESPONSE_LOCAL.remove();
    	REQUEST_LOCAL.remove();
    	USER_LOCAL.remove();//移除dubbo的信息，按道理应该没有
    }
  
    public static HttpSession getSession() {
    	HttpServletRequest req = getRequest();
    	if(req !=null){
    		HttpSession session = req.getSession();
    		log.debug("HttpServletRequest is not null, get session from httprequest."+session.getClass().getName());
    		return session;
    	}else{
    		return null;
    	}
    }

    private final static Map<String,Operator> USER_DATA = new ConcurrentHashMap<>();
    
    public static void setCurrentOperator(Operator operator){
    	USER_DATA.put(operator.getAccessToken(), operator);
    }
    
    public static void removeOperator(Operator operator){
    	if(operator!=null && operator.getAccessToken()!=null){
    		USER_DATA.remove(operator.getAccessToken());
    	}
    }
    
    public static Operator getCurrentOperator(){
    	HttpSession session = SysContent.getSession();
    	if(session == null){//说明是非http形式过来的代码，httprequest应该为空，USER_LOCAL应该已经设置过了
    		Operator operator = USER_LOCAL.get();//
    		return operator == null?Operator.getEmpty():operator;
    	}else{
    		return getCurrentOperator(session);
    	}
    }
    
    public static Operator getCurrentOperator(HttpSession session){
    	
    	Operator user = (Operator)session.getAttribute(SessionKeyEnum.CURRENTUSEROPERATOR.getCode());
    	if(user == null){
    		return Operator.EMPTY;
    	}else{
    		Operator syncOperator = null;
    		if(user.getAccessToken() != null){
    			syncOperator = USER_DATA.get(user.getAccessToken());
    		}
    		if(syncOperator==null){
    			return user;
    		}else{
    			return syncOperator;
    		}
    	}
    }
}
