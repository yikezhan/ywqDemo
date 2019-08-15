package com.yuwuquan.demo.designpatterns.decorator.vegetable;

import com.yuwuquan.demo.designpatterns.decorator.HandPancake;

public class Egg extends Decorator {

    public Egg(HandPancake handPancake) {
        super(handPancake);
    }

    @Override
    public String offerHandPancake() {
        return super.offerHandPancake() + ",加鸡蛋";
    }

    @Override
    public double calcCost() {
        return super.calcCost()+1;
    }
}
