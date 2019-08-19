package com.yuwuquan.demo.designpatterns.memento;

import java.util.ArrayList;

public class MementoCaretaker {
    //定义一个集合来存储备忘录
    private ArrayList mementolist = new ArrayList();
    public ChessMemento getMemento(int i) {
        return (ChessMemento) mementolist.get(i);
    }

    public void addMemento(ChessMemento memento) {
        mementolist.add(memento);
    }
}
