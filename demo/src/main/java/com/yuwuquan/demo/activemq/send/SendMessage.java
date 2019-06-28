package com.yuwuquan.demo.activemq.send;

import com.yuwuquan.demo.activemq.message.template.MessageDetail;

import java.util.Map;

public interface SendMessage {

	/**
	 * 发送消息内容
	 */
	<T> String sendMsg(MessageDetail<T> messageDetail);

	/**
	 * 发送带消息头 消息内容
	 * @param messageDetail
	 * @param header
	 * @param <T>
	 * @return
	 */
	<T> String sendMsg(MessageDetail<T> messageDetail, Map<String, Object> header);

}
