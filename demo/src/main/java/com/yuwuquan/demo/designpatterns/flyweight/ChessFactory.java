package com.yuwuquan.demo.designpatterns.flyweight;

import java.util.HashMap;

public class ChessFactory {
    //定义一个池容器
    private static HashMap<String,AbstractChess> pool = new HashMap<>();
    //享元工厂
    public static AbstractChess getChess(String color){
        AbstractChess chess = null;
        if(pool.containsKey(color)){
            chess = pool.get(color);
        }else{
            chess = new Chess(color);
        }
        pool.put(color,chess);
        return chess;
    }
}
