package com.yuwuquan.demo.activemq.constant;

import lombok.Getter;

@Getter
public enum ProcessResultEnum {
    FAILED(false, "失败", "处理失败"),
    SUCCESS(true, "成功", "处理成功");

    private boolean success;
    private String name;
    private String desc;
    private ProcessResultEnum(boolean success, String name, String desc){
        this.success = success;
        this.name = name;
        this.desc = desc;
    }
}
