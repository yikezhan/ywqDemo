package com.yuwuquan.demo.seckillsys.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuwuquan.demo.activemq.constant.QueueType;
import com.yuwuquan.demo.activemq.message.template.MessageDetail;
import com.yuwuquan.demo.activemq.process.impl.ModifyMessageProcess;
import com.yuwuquan.demo.activemq.process.impl.QueryMessageProcess;
import com.yuwuquan.demo.activemq.receive.ReceiveMessage;
import com.yuwuquan.demo.exception.ApplicationException;
import com.yuwuquan.demo.seckillsys.SecKillEnum;
import com.yuwuquan.demo.seckillsys.SecKillStatusEnum;
import com.yuwuquan.demo.seckillsys.dto.SecKillGoodsDTO;
import com.yuwuquan.demo.seckillsys.service.SecKillService;
import com.yuwuquan.demo.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

@Slf4j
@Service
public class SecKillConsumerDeque implements ReceiveMessage {

	@Resource(name = "MqExecutorService")
	private ExecutorService executorService;

	private static final Logger logger = LoggerFactory.getLogger(SecKillConsumerDeque.class);

	@Autowired
	private SecKillService secKillService;
	@Autowired
	RedisUtil redisUtil;

	/**
	 * 使用@JmsListener注解会自动监听，该方法不需要调用。该方法监听第一个队列。
	 * @param txtMsg
	 */
	@JmsListener(destination = QueueType.SEC_KILL_QUEUE)
	public void receiveSecKillQueueMessage(String txtMsg) {
		executorService.submit(() -> {
			try{
				MessageDetail<?> messageDetail = JSON.parseObject(txtMsg, MessageDetail.class);
				SecKillGoodsDTO secKillGoodsDTO = JSONObject.parseObject(JSON.toJSONString(messageDetail.getT()), SecKillGoodsDTO.class);
				if(secKillService.inventoryDeduction(secKillGoodsDTO.getSku())){//扣库存成功,秒杀成功
					secKillService.insertSecKillSuccessUser(secKillGoodsDTO);//记录成功的用户，成功的数目不会很大，有问题再考虑mq处理
					redisUtil.set(SecKillEnum.SEC_KILL_QUEUE_USER_PRE.getKey() + secKillGoodsDTO.getUserId(), SecKillStatusEnum.SecKillSuccess.getValue(),SecKillEnum.SEC_KILL_QUEUE_USER_PRE.getTime());
				}else{//扣库存失败,秒杀失败
					redisUtil.set(SecKillEnum.SEC_KILL_QUEUE_USER_PRE.getKey() + secKillGoodsDTO.getUserId(), SecKillStatusEnum.SecKillFail.getValue(),SecKillEnum.SEC_KILL_QUEUE_USER_PRE.getTime());
				}
			} catch (Exception e){
				log.error("消费秒杀队列失败！！！",e);
			}
		});
	}
}
