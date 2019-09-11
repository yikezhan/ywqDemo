package com.yuwuquan.demo.sourcecodeanalysis;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public abstract class GenericServlet implements Servlet,ServletConfig{
    private transient ServletConfig config;

    /**
     * tomcat调用这个方法，将配置传入进来。
     * @param config
     */
    @Override
    public void init(ServletConfig config) {
        this.config = config;
        this.init();
    }

    /**
     *  额外提供一个不需要conf参数的方法。
     *  其实个人觉得是否也是可以提供一个SetServletConfig()方法，这样就不需要两个init()了，但是又模糊了初始化这件事概念（初始化只调用一次）。
     *  空方法，供子类进行重写。（为什么不定义成抽象方法？有些需要调用这样的空方法？）
     */
    public void init() {

    }

    @Override
    public void destory() {

    }

    @Override
    public ServletConfig getServletConfig() {
        return this.config;
    }

    /**
     * 这里不实现service，交给具体的协议去实现
     * @param request
     * @param response
     */
    @Override
    public abstract void service(ServletRequest request, ServletResponse response);

    @Override
    public ServletContext getServletContext() {
        return this.config.getServletContext();
    }
}
