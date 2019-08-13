package com.yuwuquan.demo.designpatterns.usualstrategy;

/**
 * 普通的策略模式。根据类来选择不同的策略来执行。
 */
public class TestStrategy {
    public static void main(String[] args) {
        StrategyContext strategyContext = new StrategyContext(new DiscountStrategyTwo());
        System.out.println(strategyContext.doHandle(120.0));
    }
}
