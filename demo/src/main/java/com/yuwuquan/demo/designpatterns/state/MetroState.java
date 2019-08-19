package com.yuwuquan.demo.designpatterns.state;

public interface MetroState {
    boolean openDoor();
    boolean closeDoor();
    boolean run();
    boolean stop();
}
