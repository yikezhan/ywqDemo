package com.yuwuquan.demo.designpatterns.visitor.element;

import com.yuwuquan.demo.designpatterns.visitor.IVisitor;
import lombok.Data;

@Data
public abstract class Element {
    private int price;
    public abstract void accept(IVisitor visitor);
}
