package com.yuwuquan.demo.designpatterns.decorator.vegetable;

import com.yuwuquan.demo.designpatterns.decorator.HandPancake;

public class Bacon extends Decorator {

    public Bacon(HandPancake handPancake) {
        super(handPancake);
    }

    @Override
    public String offerHandPancake() {
        return super.offerHandPancake() + ",加培根";
    }

    @Override
    public double calcCost() {
        return super.calcCost()+1.5;
    }
}
