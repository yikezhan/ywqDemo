package com.yuwuquan.demo.controller.task;

import com.yuwuquan.demo.common.ResponseDTO;
import com.yuwuquan.demo.common.ResponseStatusCode;
import com.yuwuquan.demo.controller.request.PublishTaskRequest;
import com.yuwuquan.demo.controller.resonse.LoginResponse;
import com.yuwuquan.demo.controller.resonse.PublishTaskResponse;
import com.yuwuquan.demo.exception.ApplicationException;
import com.yuwuquan.demo.orm.model.PublishTask;
import com.yuwuquan.demo.orm.model.SysUserInfo;
import com.yuwuquan.demo.service.PublishTaskService;
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
import java.util.List;
import java.util.Random;

@Api(tags="发布任务")
@RestController
@RequestMapping(value = "/publishtask")
public class PublishTaskController {
    @Autowired
    PublishTaskService publishTaskService;

    @ApiOperation(value = "创建队列消息，基本信息")
    @PostMapping(value = "/createTask")
    public ResponseDTO createTask(@RequestBody PublishTask publishTask){
        PublishTaskResponse publishTaskResponse = new PublishTaskResponse();
        try {
            publishTaskService.createTask(publishTask);
        } catch (ApplicationException e) {
            return publishTaskResponse.systemFail(e);
        } catch (Exception e){
            return publishTaskResponse.systemFail();
        }
        return publishTaskResponse.success();
    }

    @ApiOperation(value = "正式发布任务")
    @PostMapping(value = "/publishTask")
    public ResponseDTO publishTask(Long id){
        PublishTaskResponse publishTaskResponse = new PublishTaskResponse();
        try {
            publishTaskService.publishTask(id);
        } catch (ApplicationException e) {
            return publishTaskResponse.systemFail(e);
        } catch (Exception e){
            return publishTaskResponse.systemFail();
        }
        return publishTaskResponse.success("发布成功");
    }

    @ApiOperation(value = "查询队列信息,如果查询指定信息，则传id")
    @PostMapping(value = "/queryTask")
    public ResponseDTO queryTask(@RequestBody PublishTaskRequest request){
        PublishTaskResponse publishTaskResponse = new PublishTaskResponse();
        List<PublishTask> publishTasks = publishTaskService.queryTask(request.getPublishTask(),request.getPaginationDTO());
        return publishTaskResponse.success(publishTasks);
    }

}
