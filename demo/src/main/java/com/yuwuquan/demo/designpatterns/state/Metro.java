package com.yuwuquan.demo.designpatterns.state;

public class Metro implements IMetro {
    private MetroState nowState;
    private MetroState openDoorState;
    private MetroState closeDoorState;
    private MetroState runState;
    private MetroState stopState;

    public Metro(MetroState nowState) {
        openDoorState = new OpenDoorMetroState();
        closeDoorState = new CloseDoorMetroState();
        runState = new RunMetroState();
        stopState = new StopMetroState();
        this.nowState = nowState;
    }

    @Override
    public void openDoor() {
        if(nowState.openDoor()){
            this.nowState = this.openDoorState;
        }
    }

    @Override
    public void closeDoor() {
        if(nowState.closeDoor()){
            this.nowState = this.closeDoorState;
        }
    }

    @Override
    public void run() {
        if(nowState.run()){
            this.nowState = this.runState;
        }
    }

    @Override
    public void stop() {
        if(nowState.stop()){
            this.nowState = this.stopState;
        }
    }
}
