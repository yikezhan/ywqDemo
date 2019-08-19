package com.yuwuquan.demo.designpatterns.chain;

import com.yuwuquan.demo.designpatterns.chain.inter.Handle;
import com.yuwuquan.demo.designpatterns.chain.inter.HandleChain;

import java.util.ArrayList;

public class Chain implements HandleChain {
    private ArrayList<Handle> handlers;
    private int pos = 0;

    public Chain(ArrayList<Handle> handlers) {
        this.handlers = handlers;
    }
    /**
     * 这里为什么不直接用foreach循环遍历？
     * 个人理解是为了可以在各个执行链上可以定义链接终止的逻辑。
     */
    @Override
    public void handler(String word) {
        if(pos < handlers.size()){
            Handle handle = handlers.get(pos);
            pos++;
            handle.doFilter(this, word);
        }
    }
}
