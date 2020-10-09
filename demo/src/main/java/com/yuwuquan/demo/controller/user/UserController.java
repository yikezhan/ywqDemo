package com.yuwuquan.demo.controller.user;

import com.yuwuquan.demo.common.ResponseDTO;
import com.yuwuquan.demo.common.ResponseStatusCode;
import com.yuwuquan.demo.controller.resonse.LoginResponse;
import com.yuwuquan.demo.exception.ApplicationException;
import com.yuwuquan.demo.orm.model.SysUserInfo;
import com.yuwuquan.demo.service.UserService;
import com.yuwuquan.demo.session.JwtTokenUtil;
import com.yuwuquan.demo.sysenum.CodeEnum;
import com.yuwuquan.demo.util.RedisUtil;
import com.yuwuquan.demo.util.common.StringUtil;
import com.yuwuquan.demo.util.encryption.PwdUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    private static boolean needImageCode = false;//是否需要图形验证码开关

    @ApiOperation(value = "快速注册并登录")
    @PostMapping(value = "/quickRegister")
    public ResponseDTO quickRegister(@RequestBody SysUserInfo sysUserInfo){
        String sysVerCode = StringUtil.valueOf(redisUtil.get(CodeEnum.IMAGE_VERIFICATION_CODE_KEY_PRE.getValue() + sysUserInfo.getPhone()), "");
        String imgCode = sysUserInfo.getImageCode();
        if(needImageCode && (imgCode == null || !imgCode.equals(sysVerCode)))//验证图形验证码
            return ResponseDTO.systemFail(ResponseStatusCode.IMG_VERIFY_CODE_ERROR);
        LoginResponse loginResponse = new LoginResponse();
        try{
            userService.insertUser(sysUserInfo);
            loginResponse.setToken(setSession(sysUserInfo));
            return loginResponse.success();
        }catch (ApplicationException e){
            return loginResponse.systemFail(e);
        }catch (Exception e){
            return loginResponse.systemFail();//唯一标识重复
        }
    }

    @ApiOperation(value = "密码登录")
    @GetMapping(value = "/loginByPassword")
    public ResponseDTO loginByPassword(@RequestParam("phone") String phone, @RequestParam("password") String password){
        LoginResponse loginResponse = new LoginResponse();
        SysUserInfo sysUserInfo = new SysUserInfo(phone);
        sysUserInfo.setPassword(PwdUtils.encryptPassword(phone,password));
        sysUserInfo = userService.queryByUser(sysUserInfo);
        if(sysUserInfo != null){
            loginResponse.setToken(setSession(sysUserInfo));
            return loginResponse.success();
        }else{
            return loginResponse.systemFail(ResponseStatusCode.USER_SECRET_CODE);
        }
    }

    @ApiOperation(value = "根据手机号更新密码")
    @GetMapping(value = "/updatePasswordByPhone")
    public ResponseDTO updatePasswordByPhone(@RequestParam("phone") String phone, @RequestParam("code") String code, @RequestParam("newPassword") String newPassword){
        Object redisCode = redisUtil.get(CodeEnum.MODIFY_PASSWORD_KEY_PRE.getValue() + phone);
        if(redisCode != null && redisCode.toString().equals(code)){
            SysUserInfo sysUserInfo = new SysUserInfo(phone);
            sysUserInfo.setPassword(newPassword);
            userService.updatePasswordByPhone(sysUserInfo);
            return ResponseDTO.success();
        }else{
            return ResponseDTO.systemFail(ResponseStatusCode.VERIFY_CODE_ERROR);
        }
    }
    @ApiOperation(value = "验证码登录")
    @GetMapping(value = "/loginByVerificationCode")
    public ResponseDTO  loginByVerificationCode(@RequestParam("phone") String phone, @RequestParam("code") String code){
        LoginResponse loginResponse = new LoginResponse();
        Object redisCode = redisUtil.get(CodeEnum.VERIFICATION_CODE_KEY_PRE.getValue() + phone);
        if(redisCode != null && redisCode.toString().equals(code)){
            SysUserInfo sysUserInfo = new SysUserInfo(phone);
            loginResponse.setToken(setSession(sysUserInfo));
            SysUserInfo sysUser = userService.queryByUser(sysUserInfo);
            if(sysUser == null){//无此账号则自动注册
                sysUserInfo.setPassword((new Random().nextInt(1000000)) + "");//随机密码
                userService.insertUser(sysUserInfo);
            }
            return loginResponse.success();
        }else{
            return loginResponse.systemFail(ResponseStatusCode.VERIFY_CODE_ERROR);
        }
    }
    @ApiOperation(value = "退出登录")
    @GetMapping(value = "/loginOut")
    public ResponseDTO  loginOut(@RequestParam("phone") String phone){
        SysUserInfo sysUserInfo = new SysUserInfo(phone);
        removeSession(sysUserInfo);
        return ResponseDTO.success();//登录页面
    }
    @ApiOperation(value = "发送验证码，包括登录、修改密码等操作")
    @GetMapping(value = "/sendCode")
    public ResponseDTO sendCode(HttpServletResponse response, HttpServletRequest request, HttpSession session, @RequestParam("phone") String phone, Integer type, String imgCode){
        CodeEnum codeEnum = CodeEnum.getCodeEnumByCode(type);
        String sysVerCode = StringUtil.valueOf(redisUtil.get(CodeEnum.IMAGE_VERIFICATION_CODE_KEY_PRE.getValue() + phone), "");
        if(needImageCode && (imgCode == null || !imgCode.equals(sysVerCode)))//验证图形验证码
            return ResponseDTO.systemFail(ResponseStatusCode.IMG_VERIFY_CODE_ERROR);
        if(codeEnum == null)
            return ResponseDTO.systemFail();
        if(redisUtil.get(codeEnum.getValue() +phone) != null)
            return ResponseDTO.systemFail(ResponseStatusCode.VERIFY_CODE_EXIST);

        String verificationCode = "12345";// TODO: 2019/12/17 发送短信操作
        redisUtil.set(CodeEnum.getCodeEnumByCode(type).getValue() + phone, verificationCode, CodeEnum.getCodeEnumByCode(type).getTime());
        return ResponseDTO.success(null);
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
