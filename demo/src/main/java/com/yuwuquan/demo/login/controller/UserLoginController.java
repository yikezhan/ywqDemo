package com.yuwuquan.demo.login.controller;

import com.yuwuquan.demo.common.ResponseDTO;
import com.yuwuquan.demo.common.ResponseStatusCode;
import com.yuwuquan.demo.controller.BaseController;
import com.yuwuquan.demo.login.account.*;
import com.yuwuquan.demo.exception.ApplicationException;
import com.yuwuquan.demo.login.service.AuthenticationService;
import com.yuwuquan.demo.session.*;
import com.yuwuquan.demo.util.common.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping("/usr")
@Api(description="用户登录",tags="UserLoginController")
public class UserLoginController extends BaseController {
	
	@Autowired
	private AuthenticationService authService;

	@RequestMapping(value="/login",method={RequestMethod.POST})
	@ApiOperation(value = "用户登录")
	@SessionRequired(SessionType.NONE)
	@ResponseBody
	public LoginDataResponse userLogin(HttpServletResponse response, HttpServletRequest request, HttpSession session, @RequestBody @Valid LoginDataRequest loginReq){
		LoginDataResponse resp = new LoginDataResponse();
		String sysVerCode = (String)session.getAttribute(SessionKeyEnum.VCODENAME.getCode());

		String firstLoginFailed = (String)session.getAttribute(SessionKeyEnum.FIRSTLOGINFAILED.getCode());

		log.info("----------------------------------------用户登录开始({})----------------------------------------", System.currentTimeMillis());
		log.info("开始验证码验证");
		if(checkVerifyCode(firstLoginFailed, sysVerCode, loginReq.getReqDtos().getVerifyCode())){
			LoginOperation.loginFailed(response, session);
			throw new ApplicationException(ResponseStatusCode.VERIFY_CODE_ERROR.getMessage(),
					ResponseStatusCode.VERIFY_CODE_ERROR.getCode());
		}

		log.info("开始用户身份验证");
		User user = authService.queryUser(loginReq.getReqDtos().getUserName(), loginReq.getReqDtos().getPassword());
		if(userInfoPass(response, session, user)){
			Operator operator = getDozerMapper().map(user, Operator.class);
			operator.setRemoteIp(LoginOperation.getClientIP(request));
			LoginOperation.afterLoginSuccess(request,response, session, operator);
		}
		log.info("----------------------------------------用户登录结束({})----------------------------------------"+System.currentTimeMillis());
		return resp;
	}

	private boolean userInfoPass(HttpServletResponse response, HttpSession session, User user) {
		//用户不存在
		if(user == null){
			LoginOperation.loginFailed(response, session);
			throw new ApplicationException(ResponseStatusCode.USER_NOT_EXISTS.getMessage(),
					ResponseStatusCode.USER_NOT_EXISTS.getCode());
		}
		//员工不在职
		if(user.getIsWorking() != 1){
			throw new ApplicationException(ResponseStatusCode.EMPLOYEE_ISEXIST_ISNOJOB.getMessage(),
					ResponseStatusCode.EMPLOYEE_ISEXIST_ISNOJOB.getCode());
		}
		return true;
	}

	private boolean checkVerifyCode(String firstLoginFailed, String sysVerCode, String verCode){
		if(StringUtil.isEmpty(firstLoginFailed))
			return true;
		if(sysVerCode.equals(verCode) && firstLoginFailed.equalsIgnoreCase(Boolean.TRUE.toString())){
			return true;
		}
		return false;
	}

	@RequestMapping(value="/logout",method={RequestMethod.GET, RequestMethod.POST})
	@SessionRequired(SessionType.NONE)
	@ApiOperation(value = "用户登出")
	public ResponseDTO logoutUser(HttpSession session){
		ResponseDTO resp = new ResponseDTO();
		Operator operator = (Operator)session.getAttribute(SessionKeyEnum.CURRENTUSEROPERATOR.getCode());
		if(operator != null) {
			SysContent.removeOperator(operator);
			session.removeAttribute(SessionKeyEnum.CURRENTUSEROPERATOR.getCode());
			session.removeAttribute(SessionKeyEnum.USERPERMISSION.getCode());
		}
		return resp;
	}

	@RequestMapping(value="/{userId}/permission",method= RequestMethod.GET)
	@ApiOperation(value = "取得用户权限")
	public @ResponseBody
    UserPermissionResponse getUserPermission(@ApiParam(value="用户ID")@PathVariable(name="userId") String userId){
		UserPermissionResponse result = new UserPermissionResponse();

		return result;
	}
	@RequestMapping(value="/current",method= RequestMethod.GET)
	@ApiOperation(value="取得当前用户信息")
	public @ResponseBody
    UserResponse getCurrentUser(){
		UserDetailDTO result = getDozerMapper().map(SysContent.getCurrentOperator(), UserDetailDTO.class);
		UserResponse r =new UserResponse();
		r.setData(result);
		return r;
	}

}
