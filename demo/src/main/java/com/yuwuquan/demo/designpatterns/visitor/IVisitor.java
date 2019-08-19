package com.yuwuquan.demo.designpatterns.visitor;

import com.yuwuquan.demo.designpatterns.visitor.element.Body;
import com.yuwuquan.demo.designpatterns.visitor.element.Engine;
import com.yuwuquan.demo.designpatterns.visitor.element.Wheel;

public interface IVisitor {
    void visit(Wheel wheel);
    void visit(Engine engine);
    void visit(Body body);
}
