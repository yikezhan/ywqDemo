package com.yuwuquan.demo.util.multithreading;


import org.springframework.beans.factory.FactoryBean;

import java.util.concurrent.*;

/**
 * @Author ywq
 * 本类为自定义的线程池抽象工厂。
 * 两个构造函数，一个自定义线程池参数配置，一个使用本类默认参数。
 * 实现FactoryBean为自带的一个工厂方法模板。
 * 可以使用本类实例化出不同应用场景下的多个线程池。
 * 核心方法getObject()使用了ThreadPoolExecutor类的构造函数。该类的详解见笔记。
 */
public class ExecutorServiceFactory implements FactoryBean<ExecutorService> {
    private int corePoolSize = 8;
    private int maximumPoolSize = 16;
    private long keepAliveTime = 10;

    public ExecutorServiceFactory() {

    }

    public ExecutorServiceFactory(int corePoolSize,int maximumPoolSize,long keepAliveTime) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
    }

    @Override
    public ExecutorService getObject() throws Exception {
        ExecutorService executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(10),Executors.defaultThreadFactory());
        return executorService;
    }

    @Override
    public Class<?> getObjectType() {
        return ExecutorService.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
