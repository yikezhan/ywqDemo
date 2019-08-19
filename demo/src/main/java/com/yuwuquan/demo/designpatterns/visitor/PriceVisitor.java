package com.yuwuquan.demo.designpatterns.visitor;

import com.yuwuquan.demo.designpatterns.visitor.element.Body;
import com.yuwuquan.demo.designpatterns.visitor.element.Engine;
import com.yuwuquan.demo.designpatterns.visitor.element.Wheel;

public class PriceVisitor implements IVisitor {
    @Override
    public void visit(Wheel wheel) {
        wheel.setPrice(100);
    }

    @Override
    public void visit(Engine engine) {
        engine.setPrice(300);
    }

    @Override
    public void visit(Body body) {
        body.setPrice(200);
    }
}
