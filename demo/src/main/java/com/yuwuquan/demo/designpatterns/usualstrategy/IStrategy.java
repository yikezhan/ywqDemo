package com.yuwuquan.demo.designpatterns.usualstrategy;

/**
 * 策略公共接口。普通的策略模式。
 * @param <P>
 */
public interface IStrategy<P> {
    boolean check(P p);
    P process(P p);
}

