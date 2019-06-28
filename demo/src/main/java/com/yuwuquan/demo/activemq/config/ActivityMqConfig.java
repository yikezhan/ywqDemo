package com.yuwuquan.demo.activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

@Configuration
@ConditionalOnExpression("${spring.activemq.enabled:true}")
public class ActivityMqConfig {
	
	@Value("${spring.activemq.broker-url}")
	private String brokerUrl;
	@Value("${spring.activemq.user}")
	private String userName;
	@Value("${spring.activemq.password}")
	private String password;
	
	@Bean
	public JmsMessagingTemplate jmsMessagingTemplate() throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(null, null, brokerUrl);
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
		cachingConnectionFactory.setSessionCacheSize(100);
		JmsMessagingTemplate template = new JmsMessagingTemplate(cachingConnectionFactory);
		return template;
	}

}
