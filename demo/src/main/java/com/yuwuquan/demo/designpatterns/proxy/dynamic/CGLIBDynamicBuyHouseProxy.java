package com.yuwuquan.demo.designpatterns.proxy.dynamic;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CGLIBDynamicBuyHouseProxy implements MethodInterceptor {
    private Object target;
    public Object getInstance(final Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
     }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("中介在帮你找房资源。");
        //注意如果这里用invoke会陷入死循环
        Object result = methodProxy.invokeSuper(o,objects);
        System.out.println("中介在帮你办理落户等事宜");
        return result;
    }
}
