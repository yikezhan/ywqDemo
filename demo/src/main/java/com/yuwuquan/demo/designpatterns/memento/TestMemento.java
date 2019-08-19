package com.yuwuquan.demo.designpatterns.memento;

public class TestMemento {
    public static void main(String[] args) {
        MementoCaretaker mc = new MementoCaretaker();
        Chess chess = new Chess("车", 1, 1);
        mc.addMemento(chess.save());//保存
        chess.setX(1);chess.setY(2);//走棋
        chess.restore(mc.getMemento(0));//悔棋到第一步
        System.out.println(chess.toString());
    }
}
