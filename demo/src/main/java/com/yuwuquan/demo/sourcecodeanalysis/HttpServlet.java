package com.yuwuquan.demo.sourcecodeanalysis;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * tomcat范畴。
 * 主要是实现了http协议相关的方法。虽然是抽象类，但是并没有抽象方法。父类的init()方法没有在这个类重写，在子类进行了重新。
 * 对于正常的servlet，在这一层基本都封装结束了，该实现的方法也都实现了。springmvc在下一层开始对init、service、destory都进行了重写。
 * 主要研究init()的流程、对于destory()、service就暂时不做深入研究，代码即省略了。
 *
 */
public abstract class HttpServlet extends GenericServlet{

    @Override
    public void service(ServletRequest request, ServletResponse response) {
        request = (HttpServletRequest)request;
        response = (HttpServletResponse)response;
        this.service(request, response);
    }


    protected void service(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("实际在处理service");
        String method = request.getMethod();
        if (method.equals("GET")) {
            this.doGet(request, response);
        } else if (method.equals("POST")) {
            this.doPost(request, response);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendError(200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendError(200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
