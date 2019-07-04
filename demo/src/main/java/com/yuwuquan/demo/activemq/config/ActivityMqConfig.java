package com.yuwuquan.demo.activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

@Configuration
@ConditionalOnExpression("${spring.activemq.enabled:true}")
@EnableJms
public class ActivityMqConfig {
	
	@Value("${spring.activemq.broker-url}")
	private String brokerUrl;
	@Value("${spring.activemq.user}")
	private String userName;
	@Value("${spring.activemq.password}")
	private String password;

	@Bean
	public ConnectionFactory connectionFactory(){
		//这里使用ActiveMQConnectionFactory连接工厂，参数调优等在这个对象里进行配置。
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(null, null, brokerUrl);
		//在此进行参数调优
		connectionFactory.getPrefetchPolicy().setQueuePrefetch(50);
		connectionFactory.setOptimizeAcknowledge(true);//ack优化选项（实际上默认情况下是开启的）
		connectionFactory.setOptimizeAcknowledgeTimeOut(5000);//ack信息最大发送周期(毫秒)
		return connectionFactory;
	}
	@Bean
	public DefaultJmsListenerContainerFactory jmsQueueListenerContainerFactory() {

		DefaultJmsListenerContainerFactory factory =
				new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
		//设置连接数
		factory.setConcurrency("3-10");
		//重连间隔时间
		factory.setRecoveryInterval(1000L);
		return factory;
	}
	@Bean
	public JmsTemplate jmsQueueTemplate(){
		return new JmsTemplate(connectionFactory());
	}

	/*@Bean
	public JmsMessagingTemplate jmsMessagingTemplate() throws JMSException{
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(null, null, brokerUrl);
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
		cachingConnectionFactory.setSessionCacheSize(100);
		JmsMessagingTemplate template = new JmsMessagingTemplate(cachingConnectionFactory);
		return template;
	}*/
}
