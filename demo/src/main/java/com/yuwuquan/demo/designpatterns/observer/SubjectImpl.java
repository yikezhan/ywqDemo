package com.yuwuquan.demo.designpatterns.observer;

import java.util.ArrayList;

public class SubjectImpl implements Subject {
    private ArrayList<Observer> observers = new ArrayList<>();
    @Override
    public void addOberver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeOberver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notify(String word) {
        //1、可以认为通知所有的观察者
        //2、可以认为所有的监听者都收到了这个消息
        //3、可以认为所有的订阅者都收到了这个消息
        for(Observer observer : observers){
            observer.speaking(word);
        }
    }
}
