package com.yuwuquan.demo.designpatterns.observer;

public class Cat implements Observer {
    public void speaking(String food){
        System.out.println("喵、喵");
    }
}
