package com.yuwuquan.demo.security.jwtcontroller;

import com.yuwuquan.demo.security.User;
import com.yuwuquan.demo.security.dto.JwtAuthenticationRequest;
import com.yuwuquan.demo.security.dto.JwtAuthenticationResponse;
import com.yuwuquan.demo.security.dto.JwtUserRegisterResponse;
import com.yuwuquan.demo.security.jwtservice.AuthService;
import com.yuwuquan.demo.session.SessionRequired;
import com.yuwuquan.demo.session.SessionType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags="JwtAuthPermissionController",description="JWT验证")
@ConditionalOnExpression("${jwt.enabled:true}")
public class JwtAuthPermissionController {

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private AuthService authService;

	@RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
	@SessionRequired(SessionType.NONE)
	@ApiOperation("创建验证Token")
	public JwtAuthenticationResponse createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
			throws AuthenticationException {
		final String token = authService.login(authenticationRequest.getUserName(),
				authenticationRequest.getPassword());
		return new JwtAuthenticationResponse(token);
	}
	
	@RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
	@SessionRequired(SessionType.NONE)
	@ApiOperation("刷新验证Token")
	public JwtAuthenticationResponse refreshAndGetAuthenticationToken(HttpServletRequest request)
			throws AuthenticationException {
		String token = request.getHeader(tokenHeader);
		String refreshedToken = authService.refresh(token);
		return new JwtAuthenticationResponse(refreshedToken);
	}
	
	@RequestMapping(value = "${jwt.route.authentication.register}", method = RequestMethod.POST)
	@SessionRequired(SessionType.MUST)
	@ApiOperation("注册jwt用户")
	public JwtUserRegisterResponse register(@RequestBody User addedUser, HttpServletRequest request) throws AuthenticationException {
		JwtUserRegisterResponse response = new JwtUserRegisterResponse();
		response.setData(authService.register(addedUser));
		return response;
	}
}
