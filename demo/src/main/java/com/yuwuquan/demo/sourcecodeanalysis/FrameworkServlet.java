package com.yuwuquan.demo.sourcecodeanalysis;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;

/**
 * springmvc的范畴。
 * 抽象类，但无抽象方法。
 * 这里对springmvc部分能重写的service、destory都进行了最终的实现，代码就不累述了。主要研究初始化。
 */
public abstract class FrameworkServlet extends HttpServletBean {
    private WebApplicationContext webApplicationContext;
    private volatile boolean refreshEventReceived;
    //onrefresh锁监控器
    private final Object onRefreshMonitor;


    public FrameworkServlet() {
        this.refreshEventReceived = false;
        this.onRefreshMonitor = new Object();
    }
    /**
     * 重写了初始化bean的方法
     */
    protected void initServletBean(){
        this.webApplicationContext = this.initWebApplicationContext();
    }

    protected WebApplicationContext initWebApplicationContext() {
        WebApplicationContext wac = null;
        //这里对相关条件对wac进行了配置

        //如果没有刷新过，则调用onRefresh()方法
        if (!this.refreshEventReceived) {
            synchronized(this.onRefreshMonitor) {
                this.onRefresh(wac);
            }
        }
        return wac;
    }
    /**
     *等待子类重写
     */
    protected void onRefresh(ApplicationContext context) {
    }
}
