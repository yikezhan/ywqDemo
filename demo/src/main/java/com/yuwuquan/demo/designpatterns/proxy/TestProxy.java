package com.yuwuquan.demo.designpatterns.proxy;

import com.yuwuquan.demo.designpatterns.proxy.dynamic.CGLIBDynamicBuyHouseProxy;
import com.yuwuquan.demo.designpatterns.proxy.dynamic.JDKDynamicBuyHouseProxy;
import com.yuwuquan.demo.designpatterns.proxy.statics.StaticBuyHouseProxy;

import java.lang.reflect.Proxy;

public class TestProxy {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        //测试静态代理
        BuyHouseInter buyHouseInter = new BuyHouseImpl();
        BuyHouseInter staticProxy = new StaticBuyHouseProxy(buyHouseInter);
        staticProxy.buy2();
            System.out.println("--------------------------华丽分隔符JDKdynamic---------------------------------");
        //测试JDK动态代理
        BuyHouseInter buyHouseInter2 = new BuyHouseImpl();
        BuyHouseInter JDKDynamicProxy = (BuyHouseInter) Proxy.newProxyInstance(BuyHouseInter.class.getClassLoader(), new Class[]{BuyHouseInter.class}, new JDKDynamicBuyHouseProxy(buyHouseInter2));
        JDKDynamicProxy.buy2();
        System.out.println("--------------------------华丽分隔符CGLIBdynamic---------------------------------");
        //测试CGLIB动态代理
        BuyHouseImpl buyHouseInter3 = new BuyHouseImpl();
        CGLIBDynamicBuyHouseProxy cglibProxy = new CGLIBDynamicBuyHouseProxy();
        BuyHouseImpl CGLIBDynamicProxy = (BuyHouseImpl) cglibProxy.getInstance(buyHouseInter3);
        CGLIBDynamicProxy.buy2();
    }
}
