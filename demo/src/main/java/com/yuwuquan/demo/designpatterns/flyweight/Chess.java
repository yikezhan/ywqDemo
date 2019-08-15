package com.yuwuquan.demo.designpatterns.flyweight;

public class Chess extends AbstractChess {

    public Chess(String color) {
        super(color);
    }
    @Override
    public void operate(int x,int y) {
        this.x = x;
        this.y = y;
    }
}
