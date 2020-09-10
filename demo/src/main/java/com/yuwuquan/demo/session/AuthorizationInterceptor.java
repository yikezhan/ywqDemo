package com.yuwuquan.demo.session;

import com.yuwuquan.demo.exception.ApplicationException;
import com.yuwuquan.demo.exception.BaseExceptionCode;
import com.yuwuquan.demo.orm.model.SysUserInfo;
import com.yuwuquan.demo.service.UserService;
import com.yuwuquan.demo.sysenum.CodeEnum;
import com.yuwuquan.demo.util.RedisUtil;
import com.yuwuquan.demo.util.common.StringUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    static ArrayList filterList=new ArrayList<>();

    /**
     * 所有用户都能访问的页面路径
     */
    static {
        //在com.yuwuquan.demo.session.InterceporConfig里增加了
//        filterList.add("/login");
        filterList.add("auth/auth/vcode");

        //秒杀系统测试，暂时不需要登录
        filterList.add("/seckill/getKey");
        filterList.add("/seckill/orderBooking");
        filterList.add("/seckill/orderBookingTest");
        filterList.add("/seckill/getRes");
        filterList.add("/seckill/setRedisKeyJobHandler");
        filterList.add("/AI/chatAnswer");


    }
    private static final String tokenHeader = "tokenHeader";
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserService userService;
    /**
     * token+redis方式验证。取出token中的phone，查看redis中该用户对应的建是否存在。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String pagePath = extractPathFromPattern(request);
        String pagePath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        //登录页面等无需权限控制
        if(filterList.contains(pagePath)){
            return true;
        }
        //获取token
        String token = request.getHeader(tokenHeader);
        if(StringUtil.isBlank(token)){//token为空，异常
            throw new ApplicationException(BaseExceptionCode.NO_AUTHORIZATION.getMessage(), BaseExceptionCode.NO_AUTHORIZATION.getCode());
        }
        String phone = jwtTokenUtil.getPhoneFromToken(token);
        if(StringUtil.isBlank(phone)){//token中不能取出phone，异常
            throw new ApplicationException(BaseExceptionCode.NO_AUTHORIZATION.getMessage(), BaseExceptionCode.NO_AUTHORIZATION.getCode());
        }
        if(redisUtil.get(CodeEnum.SESSION_KEY_PRE.getValue() + phone) != null){//用户已登录
            SysContent.setOperator(userService.queryByUser(new SysUserInfo(phone)));
            return true;
        }else{//用户未登录
            return false;
        }
    }

    private static String extractPathFromPattern(final HttpServletRequest request){
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }
}
