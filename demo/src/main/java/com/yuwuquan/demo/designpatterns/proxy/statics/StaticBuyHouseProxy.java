package com.yuwuquan.demo.designpatterns.proxy.statics;

import com.yuwuquan.demo.designpatterns.proxy.BuyHouseInter;

public class StaticBuyHouseProxy implements BuyHouseInter {
    //这个变量传进来的是实际的实体
    private BuyHouseInter buyHouseInter;

    public StaticBuyHouseProxy(final BuyHouseInter buyHouseInter) {
        this.buyHouseInter = buyHouseInter;
    }

    @Override
    public void buy() {
        System.out.println("中介在帮你找房资源。");
        buyHouseInter.buy();
        System.out.println("中介在帮你办理落户等事宜");
    }

    @Override
    public void buy2() {

        System.out.println("中介在帮你找房资源2。");
        buyHouseInter.buy2();
        System.out.println("中介在帮你办理落户等事宜2");
    }

}
