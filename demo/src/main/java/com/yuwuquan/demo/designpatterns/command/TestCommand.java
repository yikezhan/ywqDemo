package com.yuwuquan.demo.designpatterns.command;

public class TestCommand {
    public static void main(String[] args) {
        Light light = new Light();
        LightOnCommand lightOnCommand = new LightOnCommand(light);
        LightOffCommand lightOffCommand = new LightOffCommand(light);
        lightOnCommand.execute();//开灯
        lightOffCommand.execute();//关灯
    }
}
