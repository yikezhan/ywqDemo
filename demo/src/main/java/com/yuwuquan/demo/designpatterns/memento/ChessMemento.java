package com.yuwuquan.demo.designpatterns.memento;

import lombok.Data;

@Data
public class ChessMemento {
    private String label;
    private int x;
    private int y;

    public ChessMemento(String label, int x, int y) {
        this.label = label;
        this.x = x;
        this.y = y;
    }
}
