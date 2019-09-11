package com.yuwuquan.demo.sourcecodeanalysis;

import org.springframework.context.ApplicationContext;

/**
 * springmvc最重要的类来了，终于不是abstract类了。主要是实现了九大组件的初始化，
 */
public class DispatcherServlet extends FrameworkServlet {
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
}
