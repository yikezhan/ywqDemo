package com.yuwuquan.demo.designpatterns.visitor.element;

import com.yuwuquan.demo.designpatterns.visitor.IVisitor;

public class Wheel extends Element {
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
