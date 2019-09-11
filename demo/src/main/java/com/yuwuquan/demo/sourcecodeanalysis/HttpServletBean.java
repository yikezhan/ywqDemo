package com.yuwuquan.demo.sourcecodeanalysis;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;

/**
 * springmvc的范畴。
 * 实现了一个感知类，一个设置Environment的类（暂未研究，也是一个很重要的类，存储环境变量相关等），重写的是init()方法，并且置为了final。
 * 抽象类，但无抽象方法。
 */
public abstract class HttpServletBean extends HttpServlet implements EnvironmentCapable, EnvironmentAware {
    @Override
    public void setEnvironment(Environment environment) {

    }

    @Override
    public Environment getEnvironment() {
        return null;
    }

    /**
     * 重写了init方法，主要是调用initServletBean。
     * 像这种另写一个方法给子类重写的，主要还是因为在这个方法里面做了一些其他操作，不需要子类知道。和servlet有两个init方法是同一个道理。
     */
    public final void init() {
        //这里还做了一些其他操作
        this.initServletBean();
    }

    /**
     * 空方法，供真正的子类进行实现。
     */
    protected void initServletBean(){
    }
}
