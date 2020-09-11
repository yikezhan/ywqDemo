package com.yuwuquan.demo.controller.aichatmessage;

import io.goeasy.GoEasy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


@Api(tags="智能聊天相关")
@RestController
@RequestMapping(value = "/AI")
@CrossOrigin//解决跨域问题
public class AIController {
    @ApiOperation(value = "接收聊天消息，用于智能回复")
    @PostMapping(value = "/chatAnswer")
    public Object chatAnswer(String received) {
        GoEasy goEasy = new GoEasy(GoEasyConfig.regionHost, GoEasyConfig.appkey);
        AIAnswer aiAnswer = AI.chat(received);
        if(!aiAnswer.isHasError()){
            goEasy.publish(GoEasyConfig.channel, aiAnswer.getContext());
            return "OK";
        }else{
            return aiAnswer.getErrMsg();
        }
    }
}
