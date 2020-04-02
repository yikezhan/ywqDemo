package com.yuwuquan.demo.controller.user;

import com.yuwuquan.demo.common.ResponseDTO;
import com.yuwuquan.demo.sysenum.CodeEnum;
import com.yuwuquan.demo.util.RedisUtil;
import com.yuwuquan.demo.util.common.NetUtil;
import com.yuwuquan.demo.util.common.VerifyCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
	private final static Integer size = 4;//验证码位数
	@Autowired
	private RedisUtil redisUtil;
	@RequestMapping(value="/vcode.jpg",method={RequestMethod.GET})
	@ApiOperation(value="获取图片验证码")
	public ResponseDTO genAuthImage(HttpServletResponse response,String phone) throws IOException{
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(size);
		redisUtil.set(CodeEnum.IMAGE_VERIFICATION_CODE_KEY_PRE.getValue() + phone,verifyCode.toLowerCase(),CodeEnum.IMAGE_VERIFICATION_CODE_KEY_PRE.getTime());

		log.debug("verifyCode-->"+verifyCode.toLowerCase());
		int w = 100, h = 30;// 生成图片
		VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
		return new ResponseDTO().success();
	}
}