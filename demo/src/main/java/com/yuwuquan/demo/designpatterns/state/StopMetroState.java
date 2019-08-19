package com.yuwuquan.demo.designpatterns.state;

//停止状态下一个状态为开门、关门、启动
public class StopMetroState implements MetroState {

    @Override
    public boolean openDoor() {
        System.out.println("Success,开门成功");
        return true;
    }

    @Override
    public boolean closeDoor() {
        System.out.println("Error");
        return false;
    }

    @Override
    public boolean run() {
        System.out.println("Error");
        return false;
    }

    @Override
    public boolean stop() {
        System.out.println("Error");
        return false;
    }
}
