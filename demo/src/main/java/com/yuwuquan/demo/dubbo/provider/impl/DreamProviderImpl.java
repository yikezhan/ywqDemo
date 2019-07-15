package com.yuwuquan.demo.dubbo.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yuwuquan.demo.dubbo.provider.DreamProvider;
import com.yuwuquan.demo.dubbo.provider.InfoProvider;

/**
 * provider和consumer部分都独立部署
 */
@Service(version = "1.0.0",timeout = 10000,interfaceClass = DreamProvider.class)
public class DreamProviderImpl implements DreamProvider {

    @Override
    public String getDream() {
        return "梦想？恩...猜猜看呐";
    }
}
