package com.yuwuquan.demo.designpatterns.specialstrategy.strategytype;


import com.yuwuquan.demo.designpatterns.specialstrategy.strategyfactory.AbstractStrategyContextFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 注册类
 * @param <P>
 */
public abstract class AbstractStrategy<P> implements IStrategy<P>, InitializingBean {
    @Autowired
    private AbstractStrategyContextFactory strategyContextFactory;

    //所有的属性被初始化后调用
    @Override
    public void afterPropertiesSet() throws Exception {
        strategyContextFactory.registerStrategy(this);
    }
}
