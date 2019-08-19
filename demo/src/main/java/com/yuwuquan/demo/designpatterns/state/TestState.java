package com.yuwuquan.demo.designpatterns.state;

public class TestState {
    //启动状态只能转为停止状态
    //停止状态只能转为开门状态
    //开门状态只能转为关门状态
    //关门后才能转为开门状态和启动状态
    public static void main(String[] args) {
        Metro metro = new Metro(new CloseDoorMetroState());
        metro.run();
        //运行过程中开关门均失败
        metro.openDoor();
        metro.closeDoor();
        //以下状态切换均成功
        metro.stop();
        metro.openDoor();
        metro.closeDoor();
        metro.stop();
        metro.run();
    }
}
