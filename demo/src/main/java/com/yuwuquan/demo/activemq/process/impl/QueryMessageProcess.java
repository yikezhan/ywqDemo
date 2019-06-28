package com.yuwuquan.demo.activemq.process.impl;

import com.yuwuquan.demo.activemq.constant.ProcessResultEnum;
import com.yuwuquan.demo.activemq.message.template.MessageDetail;
import com.yuwuquan.demo.activemq.process.MessageProcess;
import com.yuwuquan.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryMessageProcess implements MessageProcess {

    private static final Logger logger = LoggerFactory.getLogger(QueryMessageProcess.class);
    @Autowired
    private UserService userService;

    @Override
    public ProcessResultEnum processMessage(MessageDetail<?> messageDetail) {
        logger.info("测试mq的业务处理过程。此处业务过程为：查询了一次所以的用户信息,结果如下:{}",userService.queryAll());
        return ProcessResultEnum.SUCCESS;
    }
}
