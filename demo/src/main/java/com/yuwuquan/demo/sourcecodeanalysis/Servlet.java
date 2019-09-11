package com.yuwuquan.demo.sourcecodeanalysis;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 本包内的所有类只是为了提炼servlet到dispatchServlet的核心方法调用逻辑关系流程，这之间用到的相关的类会直接引用。代码存手打，会省去一些无关的方法。
 *
 * 本类只是定义了标准，由tomcat容器管理servlet。
 * 初始化时调用init()方法（只会调用一次），如果已经初始化则会调用service方法（就会设计多线程的问题，线程池配置在tomcat的配置那，代码上service部分要考虑多线程）。
 */
public interface Servlet {
    void init(ServletConfig var1);

    void destory();

    ServletConfig getServletConfig();

    void service(ServletRequest request, ServletResponse response);
}
