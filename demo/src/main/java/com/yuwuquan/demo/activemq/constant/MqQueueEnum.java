package com.yuwuquan.demo.activemq.constant;

import lombok.Getter;

/**
 *
 */
@Getter
public enum MqQueueEnum {
    FIRSR_QUEUE(1, QueueType.FIRST, "这是第一个测试的mq队列"),
    SECOND_QUEUE(2, QueueType.SECOND, "这是第二个测试的mq队列"),
    THIRD_QUEUE(3, QueueType.THIRD, "这是第三个测试的mq队列");

    private int code;
    private String name;
    private String desc;
    private MqQueueEnum(int code, String name, String desc){
        this.code = code;
        this.name = name;
        this.desc = desc;
    }
}
