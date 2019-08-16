package com.yuwuquan.demo.designpatterns.proxy;

public class BuyHouseImpl implements BuyHouseInter {
    @Override
    public void buy() {
        System.out.println("买房中....");
    }
    @Override
    public void buy2() {
        System.out.println("买房中2....");
    }
}
