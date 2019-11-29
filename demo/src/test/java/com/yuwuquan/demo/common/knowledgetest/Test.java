package com.yuwuquan.demo.common.knowledgetest;

class A{
    void init(){
        System.out.println("A init()");
        this.init2();
    }
    void init2(){
        System.out.println("A init2()");
    }
}
class B extends A{
    void init2(){
        System.out.println("B init2()");
        super.init2();
    }
}

public class Test {
    public static void main(String[] args) {
        A a = new B();
        a.init();
//        输出为:
//        A init()
//        B init2()
//        A init2()

    }
}
