package com.yuwuquan.demo.designpatterns.visitor;

import com.yuwuquan.demo.designpatterns.visitor.element.Body;
import com.yuwuquan.demo.designpatterns.visitor.element.Element;
import com.yuwuquan.demo.designpatterns.visitor.element.Engine;
import com.yuwuquan.demo.designpatterns.visitor.element.Wheel;

public class TestVisitor {
    public static void main(String[] args) {
        Body boby = new Body();
        Engine engine = new Engine();
        Wheel wheel = new Wheel();
        IVisitor visitor1 = new InfoVisitor();
        visitor1.visit(boby);
        visitor1.visit(engine);
        visitor1.visit(wheel);
        IVisitor visitor2 = new PriceVisitor();
        //visitor2的访问类似
    }
}
