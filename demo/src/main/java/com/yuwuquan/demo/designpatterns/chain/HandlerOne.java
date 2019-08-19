package com.yuwuquan.demo.designpatterns.chain;

import com.yuwuquan.demo.designpatterns.chain.inter.Handle;
import com.yuwuquan.demo.designpatterns.chain.inter.HandleChain;

public class HandlerOne implements Handle {
    @Override
    public void doFilter(HandleChain handleChain, String word) {
        word.replace("雨","雪");
        handleChain.handler(word);
    }
}
