package com.yuwuquan.demo.designpatterns.decorator.vegetable;

import com.yuwuquan.demo.designpatterns.decorator.HandPancake;

public class Vegetable extends Decorator {

    public Vegetable(HandPancake handPancake) {
        super(handPancake);
    }

    @Override
    public String offerHandPancake() {
        return super.offerHandPancake() + ",加蔬菜";
    }

    @Override
    public double calcCost() {
        return super.calcCost()+0.5;
    }
}
