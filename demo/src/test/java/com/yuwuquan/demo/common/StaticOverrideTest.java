package com.yuwuquan.demo.common;

public class StaticOverrideTest {
    static abstract class Human{
        protected abstract void sayHello();
    }
    static class Man extends Human{
        @Override
        protected void sayHello() {
            System.out.println("hello,man");
        }
    }
    static class Woman extends Human{
        @Override
        protected void sayHello() {
            System.out.println("hello,woman");
        }
    }
    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        man.sayHello();//hello,man
        woman.sayHello();//hello,woman
    }
}
