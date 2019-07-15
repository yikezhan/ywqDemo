package com.yuwuquan.demo.dubbo.consumer.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yuwuquan.demo.dubbo.provider.DreamProvider;
import com.yuwuquan.demo.dubbo.provider.InfoProvider;
import org.springframework.stereotype.Service;
@Service
public class TestConsumerImpl{
    @Reference(version = "1.0.0",timeout = 10000)
    private InfoProvider infoProvider;
    @Reference(version = "1.0.0",timeout = 10000)
    private DreamProvider dreamProvider;

    public String getName() {
        return infoProvider.getName();
    }
    public String getSex() {
        return infoProvider.getSex();
    }
    public String getAge() {
        return infoProvider.getAge();
    }
    public String getDream() {
        return dreamProvider.getDream();
    }
}
