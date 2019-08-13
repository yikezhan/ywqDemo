package com.yuwuquan.demo.designpatterns.specialstrategy.strategytype;

public interface IStrategy<P> {
    boolean check(P p);
    void process(P p);
}
