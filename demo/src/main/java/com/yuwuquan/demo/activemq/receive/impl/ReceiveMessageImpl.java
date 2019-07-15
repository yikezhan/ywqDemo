package com.yuwuquan.demo.activemq.receive.impl;

import com.alibaba.fastjson.JSON;
import com.yuwuquan.demo.activemq.constant.QueueType;
import com.yuwuquan.demo.activemq.message.template.MessageDetail;
import com.yuwuquan.demo.activemq.process.impl.ModifyMessageProcess;
import com.yuwuquan.demo.activemq.process.impl.QueryMessageProcess;
import com.yuwuquan.demo.activemq.receive.ReceiveMessage;
import com.yuwuquan.demo.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

@Slf4j
@Service
public class ReceiveMessageImpl implements ReceiveMessage {

	@Resource(name = "MqExecutorService")
	private ExecutorService executorService;

	@Autowired
	private ModifyMessageProcess modifyMessageProcess;
	@Autowired
	private QueryMessageProcess queryMessageProcess;

	/**
	 * 使用@JmsListener注解会自动监听，该方法不需要调用。该方法监听第一个队列。
	 * @param txtMsg
	 */
	//暂时注释了下面这行，把监听的先关了
	//@JmsListener(destination = QueueType.FORTH)
	public void receiveFirstQueueMessage(String txtMsg) {
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try{
					MessageDetail<?> messageDetail = JSON.parseObject(txtMsg, MessageDetail.class);
					modifyMessageProcess.processMessage(messageDetail);
				} catch (Exception e){
					if (e instanceof ApplicationException){
						ApplicationException applicationException = (ApplicationException) e;
						log.info("MQ消息处理异常" + applicationException.getErrorMsg(), applicationException);
					}else {
						log.info("MQ消息处理异常" + e.getMessage(), e);
					}
				}
			}
		});
	}
}
