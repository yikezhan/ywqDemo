package com.yuwuquan.demo.designpatterns.usualstrategy;

/**
 * 打0.7折的策略
 */
public class DiscountStrategyTwo implements IStrategy<Double>{
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
        return price * 0.7;
    }
}

