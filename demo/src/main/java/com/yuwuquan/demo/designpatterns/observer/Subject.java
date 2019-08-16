package com.yuwuquan.demo.designpatterns.observer;

public interface Subject {
    void addOberver(Observer observer);
    void removeOberver(Observer observer);
    public void notify(String word);
}
