package com.yuwuquan.demo.controller.task;

import com.yuwuquan.demo.common.ResponseDTO;
import com.yuwuquan.demo.exception.ApplicationException;
import com.yuwuquan.demo.orm.model.PublishTask;
import com.yuwuquan.demo.orm.model.SysTaskInfo;
import com.yuwuquan.demo.orm.model.SysUserInfo;
import com.yuwuquan.demo.service.PublishTaskService;
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

import java.util.List;
import java.util.Random;

/**
 * 基础活动相关
 */
@Api(tags="活动相关")
@RestController
@RequestMapping(value = "/task")
public class TaskController {
    @Autowired
    private PublishTaskService publishTaskService;

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);


    @ApiOperation(value = "发布任务")
    @PostMapping(value = "/addPublishTask")
    public Object addPublishTask(@RequestBody PublishTask publishTask){

        ResponseDTO responseDTO = publishTaskService.addPublishTask(publishTask);
        return responseDTO;
    }
    @ApiOperation(value = "暂停任务")
    @PostMapping(value = "/pausePublishTask")
    public Object pausePublishTask(@RequestBody PublishTask publishTask){
        ResponseDTO responseDTO = publishTaskService.pausePublishTask(publishTask);
        return responseDTO;
    }
    @ApiOperation(value = "取消任务")
    @PostMapping(value = "/cancelPublishTask")
    public Object cancelPublishTask(@RequestBody PublishTask publishTask){
        ResponseDTO responseDTO = publishTaskService.cancelPublishTask(publishTask);
        return responseDTO;
    }
    @ApiOperation(value = "查询任务列表")
    @PostMapping(value = "/queryPublishTask")
    public Object queryPublishTask(@RequestBody PublishTask publishTask){
        List<PublishTask> publishTasks = publishTaskService.queryPublishTask(publishTask);
        return "任务数据列表";
    }
    @ApiOperation(value = "查询某一任务详情")
    @PostMapping(value = "/queryPublishTaskById")
    public Object queryPublishTaskById(@RequestParam Integer taskId){
//        List<PublishTask> publishTasks = publishTaskService.queryPublishTask(publishTask);
        return "任务数据列表";
    }
}
