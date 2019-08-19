package com.yuwuquan.demo.designpatterns.chain.inter;

public interface Handle {
    public void doFilter(HandleChain handleChain, String word);
}
