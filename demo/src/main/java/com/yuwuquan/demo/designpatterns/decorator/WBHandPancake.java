package com.yuwuquan.demo.designpatterns.decorator;

/**
 * WB家的手抓饼
 */
public class WBHandPancake implements HandPancake {
    @Override
    public String offerHandPancake() {
        return "wb手抓饼";
    }

    @Override
    public double calcCost() {
        return 5;
    }
}
