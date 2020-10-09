package com.yuwuquan.demo.controller.task;

import com.yuwuquan.demo.common.ResponseDTO;
import com.yuwuquan.demo.controller.request.PublishTaskRequest;
import com.yuwuquan.demo.controller.request.ReceivedTaskRequest;
import com.yuwuquan.demo.controller.resonse.PublishTaskResponse;
import com.yuwuquan.demo.exception.ApplicationException;
import com.yuwuquan.demo.orm.model.PublishTask;
import com.yuwuquan.demo.orm.model.ReceivedTask;
import com.yuwuquan.demo.service.PublishTaskService;
import com.yuwuquan.demo.service.ReceivedTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags="发布任务")
@RestController
@RequestMapping(value = "/publishtask")
public class ReceivedTaskController {
    @Autowired
    ReceivedTaskService receivedTaskService;

    private ReceivedTask pullTask() {
        // TODO: 2020/10/9 队列中拉取任务，并做过滤。不合适的消息需要重新入队 
        return new ReceivedTask();
    }

    @ApiOperation(value = "接受任务")
    @PostMapping(value = "/receivedTask")
    public ResponseDTO receivedTask(){
        PublishTaskResponse publishTaskResponse = new PublishTaskResponse();
        try {
            ReceivedTask receivedTask = pullTask();
            receivedTaskService.receivedTask(receivedTask);
        } catch (Exception e){
            return publishTaskResponse.systemFail();
        }
        return publishTaskResponse.success();
    }


    @ApiOperation(value = "提交答案")
    @PostMapping(value = "/commitAnswer")
    public ResponseDTO commitAnswer(Long id, String answer, boolean isGiveUp){
        PublishTaskResponse publishTaskResponse = new PublishTaskResponse();
        boolean result = false;
        try {
            result = receivedTaskService.commitAnswer(id, answer, isGiveUp);
        } catch (Exception e){
            return publishTaskResponse.systemFail();
        }
        if(result){
            return publishTaskResponse.success("正确，获得赏金");
        }else{
            return publishTaskResponse.success("错误或放弃任务，未获得赏金");
        }
    }
    @ApiOperation(value = "查询接受的任务")
    @PostMapping(value = "/queryTask")
    public ResponseDTO queryTask(@RequestBody ReceivedTaskRequest request){
        PublishTaskResponse publishTaskResponse = new PublishTaskResponse();
        List<ReceivedTask> receivedTasks = receivedTaskService.queryTask(request.getReceivedTask(),request.getPaginationDTO());
        return publishTaskResponse.success(receivedTasks);
    }
}
