package com.yuwuquan.demo.designpatterns.state;

//关门状态的下一个状态是开门
public class CloseDoorMetroState implements MetroState {

    @Override
    public boolean openDoor() {
        System.out.println("Success,门已开启");
        return true;
    }

    @Override
    public boolean closeDoor() {
        System.out.println("Error");
        return false;
    }


    @Override
    public boolean run() {
        System.out.println("Success,启动成功");
        return true;
    }

    @Override
    public boolean stop() {
        System.out.println("Error");
        return false;
    }
}
