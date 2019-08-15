package com.yuwuquan.demo.designpatterns.decorator.vegetable;

import com.yuwuquan.demo.designpatterns.decorator.HandPancake;

/**
 * 该类为装饰器的抽象类。
 */
public abstract class Decorator implements HandPancake{
    private HandPancake handPancake;

    public Decorator(HandPancake handPancake) {
        this.handPancake = handPancake;
    }

    @Override
    public String offerHandPancake(){
        return handPancake.offerHandPancake();
    }
    @Override
    public double calcCost(){
        return handPancake.calcCost();
    }

}
