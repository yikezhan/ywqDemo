package com.yuwuquan.demo.designpatterns.visitor;

import com.yuwuquan.demo.designpatterns.visitor.element.Body;
import com.yuwuquan.demo.designpatterns.visitor.element.Engine;
import com.yuwuquan.demo.designpatterns.visitor.element.Wheel;

public class InfoVisitor implements IVisitor {
    @Override
    public void visit(Wheel wheel) {
        System.out.println("轮胎");
    }

    @Override
    public void visit(Engine engine) {
        System.out.println("引擎");
    }

    @Override
    public void visit(Body body) {
        System.out.println("车身");
    }
}
