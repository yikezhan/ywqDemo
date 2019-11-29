package com.yuwuquan.demo.sourcecodeanalysis;

import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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


    /*****************************************************以下为初始化部分***********************************************************/
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


    /*****************************************************以下为执行部分***********************************************************/
    protected abstract void doService(HttpServletRequest request, HttpServletResponse response)throws Exception;

    /**
     * doGet、doPost等统一走processRequest(),与父类那些刚好相反
     */
    protected final void processRequest(HttpServletRequest request, HttpServletResponse response){
        //一些其他操作
        try {
            doService(request, response);
        }catch (Exception e){

        }
        //一些其他操作
    }
    protected void service(HttpServletRequest request, HttpServletResponse response){
        HttpMethod httpMethod = HttpMethod.resolve(request.getMethod());
        if (httpMethod == HttpMethod.PATCH || httpMethod == null) {//处理PATCH请求，直接调用processRequest()
            processRequest(request, response);
        }
        else {
            super.service(request, response);//父类的service会调用this.doGet()等方法，依旧会走到当前类的doGet()等方法中
        }
    }

    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response){
        processRequest(request, response);
    }

    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response){
        processRequest(request, response);
    }
}
