package com.yuwuquan.demo.config;

import com.yuwuquan.demo.util.multithreading.ExecutorServiceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;

/**
 * @Author ywq
 * 通用代码的多线程执行时线程池的配置
 */
@Configuration
public class UsualMultiThreadConfig {

    public static final String BEANNAME = "UsualExecutorService";

    private int corePoolSize = 8;
    private int maximumPoolSize = 16;
    private long keepAliveTime = 10000;

    @Bean(name = BEANNAME)
    public ExecutorService createExecutorService() throws Exception {
        ExecutorServiceFactory executorServiceFactory = new ExecutorServiceFactory(corePoolSize, maximumPoolSize, keepAliveTime);
        return executorServiceFactory.getObject();
    }
}
