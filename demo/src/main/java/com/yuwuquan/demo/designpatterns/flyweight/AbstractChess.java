package com.yuwuquan.demo.designpatterns.flyweight;

import lombok.Getter;

public abstract class AbstractChess {
    //内蕴状态，共享部分。必须只在构造器中赋值。且为final，赋值后不会改变。
    @Getter
    private final String color;

    public AbstractChess(String color) {
        if(color.equalsIgnoreCase("W")){
            this.color = "white";
        }else{
            this.color = "black";
        }
    }
    //外蕴状态。非共享部分。只能存储在客户端。
    protected int x;
    protected int y;
    //业务操作
    public abstract void operate(int x, int y);
    public String showChessInfo(){
        return  getColor()+"("+x+","+y+")";
    }

}
