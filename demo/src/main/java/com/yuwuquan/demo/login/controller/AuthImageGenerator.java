package com.yuwuquan.demo.login.controller;

import com.yuwuquan.demo.common.ResponseDTO;
import com.yuwuquan.demo.common.ResponseStatusCode;
import com.yuwuquan.demo.login.service.common.VerifyCodeUtils;
import com.yuwuquan.demo.session.SessionKeyEnum;
import com.yuwuquan.demo.session.SessionRequired;
import com.yuwuquan.demo.session.SessionType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/auth")
@Slf4j
@Api(tags="AuthImageGenerator",description="验证码生成器")
public class AuthImageGenerator {

	@RequestMapping(value="/vcode.jpg",method={RequestMethod.GET})
	@SessionRequired(SessionType.NONE)
	@ApiOperation(value="获取图片验证码")
	public ResponseDTO genAuthImage(HttpServletResponse response, HttpSession session) throws IOException{
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		// 删除以前的
		session.removeAttribute(SessionKeyEnum.VCODENAME.getCode());
		session.setAttribute(SessionKeyEnum.VCODENAME.getCode(), verifyCode.toLowerCase());
		log.debug("verifyCode-->"+verifyCode.toLowerCase());
		// 生成图片
		int w = 100, h = 30;
		VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
		ResponseDTO dto=new ResponseDTO();
		dto.success(ResponseStatusCode.SUCCESS.getMessage());
		return dto;
	}
}