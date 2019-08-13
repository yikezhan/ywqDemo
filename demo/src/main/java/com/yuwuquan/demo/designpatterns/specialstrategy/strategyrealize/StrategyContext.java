package com.yuwuquan.demo.designpatterns.specialstrategy.strategyrealize;

import com.yuwuquan.demo.designpatterns.specialstrategy.exception.StrategyExceptionContent;
import com.yuwuquan.demo.designpatterns.specialstrategy.strategytype.IStrategy;

public interface StrategyContext<P> {
    //设置同步策略
    void addStrategy(int key, IStrategy s);

    //设置异步策略
    void addAsynchronousStrategy(IStrategy s);

    //处理业务异常，暂未使用
    default StrategyExceptionContent<P> handleBusinessException(P p, Exception e){
        //" not support";
        return null;
    }
}
