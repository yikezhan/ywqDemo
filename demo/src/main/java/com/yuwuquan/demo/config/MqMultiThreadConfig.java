package com.yuwuquan.demo.config;

import com.yuwuquan.demo.util.multithreading.ExecutorServiceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;

/**
 * @Author ywq
 * 本类的目的是从ExecutorServiceFactory工厂实例化一个mq的线程池，并注入进spring容器，其他类可以直接使用。
 * 本类的线程池参数可以考虑放置配置中心配置。
 *
 */
@Configuration
public class MqMultiThreadConfig {

    //mq执行器
    public static final String BEANNAME = "MqExecutorService";

    private int corePoolSize = 8;
    private int maximumPoolSize = 16;
    private long keepAliveTime = 10000;

    @Bean(name = BEANNAME)
    public ExecutorService createExecutorService() throws Exception {
        ExecutorServiceFactory executorServiceFactory = new ExecutorServiceFactory(corePoolSize, maximumPoolSize, keepAliveTime);
        return executorServiceFactory.getObject();
    }
}
