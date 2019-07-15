package com.yuwuquan.demo.dubbo.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yuwuquan.demo.dubbo.provider.InfoProvider;

@Service(version = "1.0.0",timeout = 10000,interfaceClass = InfoProvider.class)
public class InfoProviderImpl  implements InfoProvider {
    @Override
    public String getName() {
        return "my name is ywq";
    }

    @Override
    public String getSex() {
        return "male";
    }

    @Override
    public String getAge() {
        return "It is a secret!";
    }
}
