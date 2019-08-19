package com.yuwuquan.demo.designpatterns.chain;

import com.yuwuquan.demo.designpatterns.chain.inter.Handle;
import com.yuwuquan.demo.designpatterns.chain.inter.HandleChain;

public class HandlerTwo implements Handle {
    @Override
    public void doFilter(HandleChain handleChain, String word) {
        //如果这里需要终止链接，则不调用这个执行链即可。
        handleChain.handler(word);
    }
}
