package com.yuwuquan.demo.activemq.message.template;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用于定义消息队列的通用信息的模板类，其中T是真正的队列要传输的消息实体。
 * @param <T>
 */
@ToString
public class MessageDetail<T> {

	@Setter
	@Getter
	private T t;

	@Setter
	@Getter
	private Class<?> tType;

	@Setter
	@Getter
	private Integer processType;

	@Setter
	@Getter
	private String queueName;
	
}
