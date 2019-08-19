package com.yuwuquan.demo.designpatterns.chain;

import com.yuwuquan.demo.designpatterns.chain.inter.Handle;

import java.util.ArrayList;

public class TestChain {
    public static void main(String[] args) {
        //初始化
        ArrayList<Handle> handles = new ArrayList<>();
        Handle handle1 = new HandlerOne();
        Handle handle2 = new HandlerTwo();
        handles.add(handle1);handles.add(handle2);
        Chain chain = new Chain(handles);
        //执行
        chain.handler("今天下大雨");
    }
}
