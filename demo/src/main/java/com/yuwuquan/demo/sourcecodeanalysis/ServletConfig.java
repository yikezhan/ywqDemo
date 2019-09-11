package com.yuwuquan.demo.sourcecodeanalysis;

import javax.servlet.ServletContext;

/**
 * 定义获取ServletContext，这部分这里不做分析。ServletContext的实现类有：ApplicationContext。
 */
public interface ServletConfig {
    ServletContext getServletContext();
}
