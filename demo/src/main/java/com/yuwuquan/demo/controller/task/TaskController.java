package com.yuwuquan.demo.controller.task;

import com.yuwuquan.demo.exception.ApplicationException;
import com.yuwuquan.demo.orm.model.SysUserInfo;
import com.yuwuquan.demo.service.TaskService;
import com.yuwuquan.demo.service.UserService;
import com.yuwuquan.demo.sysenum.CodeEnum;
import com.yuwuquan.demo.util.RedisUtil;
import com.yuwuquan.demo.util.encryption.PwdUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Api(tags="任务相关")
@RestController
@RequestMapping(value = "/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);


    @ApiOperation(value = "快速注册并登录")
    @PostMapping(value = "/addTask")
    public Object addTask(@RequestBody SysUserInfo sysUserInfo){
        try{
//            userService.insertUser(sysUserInfo);
//            setSession(sysUserInfo);
            return "注册成功,已自动登录";
        }catch (ApplicationException e){
            return "参数校验失败";
        }catch (Exception e){
            return "该用户已存在";
        }
    }
}
