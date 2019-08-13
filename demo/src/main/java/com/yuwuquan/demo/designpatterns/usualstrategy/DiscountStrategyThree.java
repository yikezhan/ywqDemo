package com.yuwuquan.demo.designpatterns.usualstrategy;

/**
 * 打0.6折的策略
 */
public class DiscountStrategyThree implements IStrategy<Double>{
    @Override
    public boolean check(Double price) {
        if(price < 10){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public Double process(Double price) {
        return price * 0.6;
    }
}
