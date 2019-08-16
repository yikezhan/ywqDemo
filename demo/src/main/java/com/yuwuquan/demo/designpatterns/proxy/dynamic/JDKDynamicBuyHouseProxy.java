package com.yuwuquan.demo.designpatterns.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JDKDynamicBuyHouseProxy implements InvocationHandler {
    private Object object;
    public JDKDynamicBuyHouseProxy(final Object object) {
        this.object = object;
    }
    //Object proxy：指被代理的对象。
    //Method method：要调用的方法
    //Object[] args：方法调用时所需要的参数
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("中介在帮你找房资源。");
        Object result = method.invoke(object, args);
        System.out.println("中介在帮你办理落户等事宜");
        return result;
    }
}
