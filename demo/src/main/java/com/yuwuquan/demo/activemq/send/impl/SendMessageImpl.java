package com.yuwuquan.demo.activemq.send.impl;

import com.alibaba.fastjson.JSON;
import com.yuwuquan.demo.activemq.message.template.MessageDetail;
import com.yuwuquan.demo.activemq.send.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


@Component
@Slf4j
public class SendMessageImpl implements SendMessage {
	
	@Value("${spring.activemq.enabled:true}")
	private String enable;

	@Resource(name = "MqExecutorService")
	private ExecutorService executorService;
	
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	
	@Override
	public <T> String sendMsg(MessageDetail<T> messageDetail){
		try {
			if(BooleanUtils.toBoolean(enable)){
				if(jmsMessagingTemplate != null && messageDetail != null){
					Future<String> res = executorService.submit(new Callable<String>() {
						/**
						 * Computes a result, or throws an exception if unable to do so.
						 *
						 * @return computed result
						 * @throws Exception if unable to compute a result
						 */
						@Override
						public String call() throws Exception {
							jmsMessagingTemplate.convertAndSend(messageDetail.getQueueName(), JSON.toJSONString(messageDetail));
							return "true";
						}
					});
					return res.get();
				}
			}
			return "false";
		}catch (Exception e){
			log.error(e.getMessage(), e);
		}
		return "false";
	}

    @Override
    public <T> String sendMsg(MessageDetail<T> messageDetail, Map<String,Object> header){
        try {
            if(BooleanUtils.toBoolean(enable)){
                if(jmsMessagingTemplate != null && messageDetail != null){
                    Future<String> res = executorService.submit(new Callable<String>() {
                        /**
                         * Computes a result, or throws an exception if unable to do so.
                         *
                         * @return computed result
                         * @throws Exception if unable to compute a result
                         */
                        @Override
                        public String call() throws Exception {
                            jmsMessagingTemplate.convertAndSend(messageDetail.getQueueName(), JSON.toJSONString(messageDetail.getT()),header);
                            return "true";
                        }
                    });
                    return res.get();
                }
            }
            return "false";
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return "false";
    }
}
