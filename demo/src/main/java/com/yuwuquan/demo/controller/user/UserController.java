package com.yuwuquan.demo.controller.user;

import com.yuwuquan.demo.exception.ApplicationException;
import com.yuwuquan.demo.orm.model.SysUserInfo;
import com.yuwuquan.demo.service.UserService;
import com.yuwuquan.demo.session.JwtTokenUtil;
import com.yuwuquan.demo.session.SysContent;
import com.yuwuquan.demo.sysenum.CodeEnum;
import com.yuwuquan.demo.util.RedisUtil;
import com.yuwuquan.demo.util.encryption.PwdUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.util.Random;

@Api(tags="用户信息相关")
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisUtil redisUtil;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @ApiOperation(value = "快速注册并登录")
    @PostMapping(value = "/quickRegister")
    public Object quickRegister(@RequestBody SysUserInfo sysUserInfo){
        try{
            userService.insertUser(sysUserInfo);
            String token = setSession(sysUserInfo);
            return "注册成功,已自动登录。返回token";
        }catch (ApplicationException e){
            return "参数校验失败";
        }catch (Exception e){
            return "该用户已存在";
        }
    }

    @ApiOperation(value = "密码登录")
    @GetMapping(value = "/loginByPassword")
    public Object loginByPassword(@RequestParam("phone") String phone, @RequestParam("password") String password){
        SysUserInfo sysUserInfo = new SysUserInfo(phone);
        sysUserInfo.setPassword(PwdUtils.encryptPassword(phone,password));
        sysUserInfo = userService.queryByUser(sysUserInfo);
        if(sysUserInfo != null){
            String token = setSession(sysUserInfo);
            return "登录成功。返回token";
        }else{
            return "密码错误";
        }
    }

    @ApiOperation(value = "根据手机号更新密码")
    @GetMapping(value = "/updatePasswordByPhone")
    public Object updatePasswordByPhone(@RequestParam("phone") String phone, @RequestParam("code") String code, @RequestParam("newPassword") String newPassword){
        Object redisCode = redisUtil.get(CodeEnum.MODIFY_PASSWORD_KEY_PRE.getValue() + phone);
        if(redisCode != null && redisCode.toString().equals(code)){
            SysUserInfo sysUserInfo = new SysUserInfo(phone);
            sysUserInfo.setPassword(newPassword);
            userService.updatePasswordByPhone(sysUserInfo);
            return "修改成功";
        }else{
            return "验证码错误";
        }
    }
    @ApiOperation(value = "验证码登录")
    @GetMapping(value = "/loginByVerificationCode")
    public Object  loginByVerificationCode(@RequestParam("phone") String phone, @RequestParam("code") String code){
        Object redisCode = redisUtil.get(CodeEnum.VERIFICATION_CODE_KEY_PRE.getValue() + phone);
        if(redisCode != null && redisCode.toString().equals(code)){
            SysUserInfo sysUserInfo = new SysUserInfo(phone);
            String token = setSession(sysUserInfo);
            SysUserInfo sysUser = userService.queryByUser(sysUserInfo);
            if(sysUser == null){//无此账号则自动注册
                sysUserInfo.setPassword((new Random().nextInt(1000000))+"");//随机密码
                userService.insertUser(sysUserInfo);
            }
            return "登录成功。返回token";
        }else{
            return "验证码错误";
        }
    }
    @ApiOperation(value = "退出登录")
    @GetMapping(value = "/loginOut")
    public Object  loginOut(@RequestParam("phone") String phone){
        SysUserInfo sysUserInfo = new SysUserInfo(phone);
        removeSession(sysUserInfo);
        return "退出成功";//登录页面
    }
    @ApiOperation(value = "发送验证码，包括登录、修改密码等操作")
    @GetMapping(value = "/sendCode")
    public Object sendCode(@RequestParam("phone") String phone, Integer code){
        CodeEnum codeEnum = CodeEnum.getCodeEnumByCode(code);
        if(codeEnum == null) return "不合法code参数";
        if(redisUtil.get(codeEnum.getValue() +phone) != null){
            return "验证码已存在，有效期内不能重复发送";
        }
        String verificationCode = "12345";// TODO: 2019/12/17 发送短信操作
        redisUtil.set(CodeEnum.getCodeEnumByCode(code).getValue() + phone, verificationCode, CodeEnum.getCodeEnumByCode(code).getTime());
        return verificationCode;
    }

    //登录成功，设置session
    private String setSession(SysUserInfo sysUserInfo){
        String token = jwtTokenUtil.generateToken(sysUserInfo);//这里生成的token里面的有效时间不做处理
        redisUtil.set(CodeEnum.SESSION_KEY_PRE.getValue() + sysUserInfo.getPhone(),1,CodeEnum.SESSION_KEY_PRE.getTime());
        return token;
    }
    private void removeSession(SysUserInfo sysUserInfo){
        if(redisUtil.get(CodeEnum.SESSION_KEY_PRE.getValue() + sysUserInfo.getPhone()) != null){
            redisUtil.del(CodeEnum.SESSION_KEY_PRE.getValue() + sysUserInfo.getPhone());
        }
    }
}
