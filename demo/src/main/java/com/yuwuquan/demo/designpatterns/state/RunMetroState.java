package com.yuwuquan.demo.designpatterns.state;

//运行状态的下一个状态是停止
public class RunMetroState implements MetroState {

    @Override
    public boolean openDoor() {
        System.out.println("Error");
        return false;
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
        System.out.println("Success，停止成功");
        return true;
    }
}
