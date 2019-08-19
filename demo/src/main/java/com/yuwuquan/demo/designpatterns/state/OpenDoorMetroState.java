package com.yuwuquan.demo.designpatterns.state;

//开门状态的下一个状态是关门
public class OpenDoorMetroState implements MetroState {

    @Override
    public boolean openDoor() {
        System.out.println("Error");
        return false;
    }

    @Override
    public boolean closeDoor() {
        System.out.println("Success，门已关闭");
        return true;
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
