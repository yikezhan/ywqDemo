package com.yuwuquan.demo.designpatterns.decorator;

import com.yuwuquan.demo.designpatterns.decorator.vegetable.Bacon;
import com.yuwuquan.demo.designpatterns.decorator.vegetable.Decorator;
import com.yuwuquan.demo.designpatterns.decorator.vegetable.Egg;
import com.yuwuquan.demo.designpatterns.decorator.vegetable.Vegetable;

/**
 * ywq家的手抓饼,装饰器模式测试。
 */
public class YWQHandPancake implements HandPancake {
    @Override
    public String offerHandPancake() {
        return "ywq手抓饼";
    }

    @Override
    public double calcCost() {
        return 5;
    }

    /**
     * new Vegetable(new Egg(new Bacon(handPancake)));看这个形式装饰器的含义像是在handPancake外面挂了点其他东西吧。
     * 手抓饼是包在里面的，不恰当。
     * @param args
     */
    public static void main(String[] args) {
        //测试类型1
        HandPancake handPancake = new YWQHandPancake();
        Decorator decorator = new Vegetable(new Egg(new Bacon(handPancake)));
        System.out.println(decorator.offerHandPancake());
        System.out.println(decorator.calcCost());
        //测试类型2
        HandPancake handPancake2 = new WBHandPancake();
        Decorator decorator2 = new Vegetable(handPancake2);
        System.out.println(decorator2.offerHandPancake());
        System.out.println(decorator2.calcCost());

    }
}
