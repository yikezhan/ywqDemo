package com.yuwuquan.demo.sourcecodeanalysis;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * springmvc最重要的类来了，终于不是abstract类了。主要是实现了九大组件的初始化。
 * 大致说下以下几个核心的接口及子类
 * 1、HandlerMapping:接口：
 * ① HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception;//用来获取具体的处理器。
 * 子类 AbstractHandlerMapping 完成了具体的实现。
 * 继承 AbstractHandlerMapping 的 AbstractUrlHandlerMapping 的类有对类注解@controller等的解析.
 * 继承 AbstractHandlerMapping 的 AbstractHandlerMethodMapping 的类有对方法注解@requestMapping等的解析。
 * 其继承的类图详见笔记。具体的看资料和源码了解。
 * 2、HandlerAdapter接口:
 * ①boolean supports(Object handler);//判断是否支持传入的handler
 * ②ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;//处理handler请求
 * ③long getLastModified(HttpServletRequest request, Object handler);//获取资源最后一次修改的时间戳值
 * 子类 HttpRequestHandlerAdapter:适配HttpRequestHandler
 * 子类 SimplerServletHandlerAdapter:适配Servlet的handler
 * 子类 SimplerControllerHandlerAdapter:适配Controller的handler
 * 子类 AbstractHandlerMethodAdapter:设置了顺序，调用了父类的模板方法
 * 继承 AbstractHandlerMethodAdapter 的子类 RequestMappingHandlerAdapter 比较复杂（书中原话是称为整个springmvc中最复杂的组件）
 *      主要是这个方法实际使用了handler处理请求。最重要的就是handleInternal方法。步骤如下：
 *      》备好处理器所需参数（最麻烦，参数类型个数都是不确定的，使用了大量组件）
 *      》使用处理器处理请求（简单）
 *      》处理返回值，统一处理成ModelAndView类型（还算简单）
 *
 * 其继承的类图详见笔记。具体的看资料和源码了解。
 */
public class DispatcherServlet extends FrameworkServlet implements ApplicationContextAware {
/************************************************以下为初始化部分***********************************************************/

    protected void onRefresh(ApplicationContext context) {
        this.initStrategies(context);
    }
    /**
     * 进行实际的9大组件的初始化
     * @param context
     */
    protected void initStrategies(ApplicationContext context) {
        this.initMultipartResolver(context);
        this.initLocaleResolver(context);
        this.initThemeResolver(context);
        this.initHandlerMappings(context);
        this.initHandlerAdapters(context);
        this.initHandlerExceptionResolvers(context);
        this.initRequestToViewNameTranslator(context);
        this.initViewResolvers(context);
        this.initFlashMapManager(context);
    }

    private void initFlashMapManager(ApplicationContext context) {

    }

    private void initViewResolvers(ApplicationContext context) {

    }

    private void initRequestToViewNameTranslator(ApplicationContext context) {

    }

    private void initHandlerExceptionResolvers(ApplicationContext context) {

    }

    private void initHandlerAdapters(ApplicationContext context) {

    }

    private void initHandlerMappings(ApplicationContext context) {

    }

    private void initThemeResolver(ApplicationContext context) {

    }

    private void initLocaleResolver(ApplicationContext context) {

    }

    private void initMultipartResolver(ApplicationContext context) {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    /************************************************以下为执行部分***********************************************************/

    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //一些其他操作
        doDispatch(request, response);
        //一些其他操作
    }

    /**
     * 核心步骤如下
     * @param request
     * @param response
     * @throws Exception
     */
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            ModelAndView mv = null;
            Exception dispatchException = null;
            HttpServletRequest processedRequest = request;
            HandlerExecutionChain mappedHandler = getHandler(processedRequest);//1、由请求获取映射器，返回执行链
            HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());//2、由映射器获取适配器
//            if (!mappedHandler.applyPreHandle(processedRequest, response)) {//①拦截器的preHandle方法执行
//                return;
//            }
            mv = ha.handle(processedRequest, response, mappedHandler.getHandler());//3、适配器内部执行时间执行器
//            mappedHandler.applyPostHandle(processedRequest, response, mv);//②拦截器的postHandle方法执行
            processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);//4、处理得到的数据，包括转为视图层
        }catch (Exception e){

        }
    }
    protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {return null;}
    protected HandlerAdapter getHandlerAdapter(Object handler) throws ServletException {return null;}
    private void processDispatchResult(HttpServletRequest request, HttpServletResponse response, @Nullable HandlerExecutionChain mappedHandler, @Nullable ModelAndView mv, @Nullable Exception exception) throws Exception {}

}
