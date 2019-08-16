package com.yuwuquan.demo.designpatterns.observer;

public class testObserve {
    public static void main(String[] args) {
        Observer cat = new Cat();
        Observer dog = new Dog();
        Subject subject = new SubjectImpl();
        subject.addOberver(cat);
        subject.addOberver(dog);
        subject.notify("吃的");
    }
}
