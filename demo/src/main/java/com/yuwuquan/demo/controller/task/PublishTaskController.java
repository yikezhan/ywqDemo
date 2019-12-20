package com.yuwuquan.demo.controller.task;

import com.yuwuquan.demo.orm.model.SysTaskInfo;
import com.yuwuquan.demo.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 发布任务相关
 */
@Api(tags="发布任务相关")
@RestController
@RequestMapping(value = "/publishtask")
public class PublishTaskController {
    @Autowired
    private TaskService taskService;

    private static final Logger logger = LoggerFactory.getLogger(PublishTaskController.class);


    @ApiOperation(value = "查询所有进行中的活动")
    @PostMapping(value = "/queryAllTask")
    public Object queryAllTask(@RequestBody SysTaskInfo sysTaskInfo){
        List<SysTaskInfo> sysTaskInfos = taskService.queryAllTask(sysTaskInfo);
        //包装一层;
        return "当前进行中的活动清单";
    }
}
