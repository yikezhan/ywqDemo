package com.yuwuquan.demo.activemq.process.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuwuquan.demo.activemq.constant.ProcessResultEnum;
import com.yuwuquan.demo.activemq.message.messagedetail.FirstKindMessageDetail;
import com.yuwuquan.demo.activemq.message.messagedetail.SecondKindMessageDetail;
import com.yuwuquan.demo.activemq.message.template.MessageDetail;
import com.yuwuquan.demo.activemq.process.MessageProcess;
import com.yuwuquan.demo.orm.model.User;
import com.yuwuquan.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModifyMessageProcess implements MessageProcess {

    private static final Logger logger = LoggerFactory.getLogger(ModifyMessageProcess.class);
    @Autowired
    private UserService userService;

    @Override
    public ProcessResultEnum processMessage(MessageDetail<?> messageDetail) {
        //在发送端发送过来的是User对象，利用JSON传输后可以转为FirstKindMessageDetail对象。说明：发送和接收只需要定义好有哪些字段。类的形式各自定义就好了。
        FirstKindMessageDetail firstKindMessageDetail = JSONObject.parseObject(JSON.toJSONString(messageDetail.getT()), FirstKindMessageDetail.class);
        User user = new User();
        user.setId(firstKindMessageDetail.getId());
        user.setName(firstKindMessageDetail.getName());
        user.setAddress(firstKindMessageDetail.getAddress());
        userService.modifyById(user);
        return ProcessResultEnum.SUCCESS;
    }
}
