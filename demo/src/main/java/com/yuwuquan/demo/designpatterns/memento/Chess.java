package com.yuwuquan.demo.designpatterns.memento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Chess {
    private String label;
    private int x;
    private int y;
    //保存状态
    public ChessMemento save() {
        return new ChessMemento(this.label, this.x, this.y);
    }
    //恢复状态
    public void restore(ChessMemento memento) {
        this.label = memento.getLabel();
        this.x = memento.getX();
        this.y = memento.getY();
    }
}
