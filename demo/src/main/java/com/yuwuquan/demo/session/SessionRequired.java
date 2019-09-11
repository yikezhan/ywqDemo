package com.yuwuquan.demo.session;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解在拦截器里面做处理。（目前还没写拦截器）
 */
@Retention(RetentionPolicy.RUNTIME)  
@Target(value=ElementType.METHOD)
public @interface SessionRequired {
	
    /** 
     * Session中用户的类型<br/> 
     * 默认 USER 
     *  
     * @return 
     */  
    SessionType value() default SessionType.NONE;  
}  