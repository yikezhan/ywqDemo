package com.yuwuquan.demo.activemq.constant;

import lombok.Getter;

/**
 *
 */
@Getter
public enum MqQueueEnum {
    FIRSR_QUEUE(1, QueueType.FIRST, "这是第一个测试的mq队列"),
    SECOND_QUEUE(2, QueueType.SECOND, "这是第二个测试的mq队列"),
    THIRD_QUEUE(3, QueueType.THIRD, "这是第三个测试的mq队列"),
    FORTH_QUEUE(4, QueueType.FORTH, "根据id更新用户信息队列"),


    SEC_KILL_QUEUE(5, QueueType.SEC_KILL_QUEUE, "秒杀抢购队列"),
    ;
    //code字段可以预留为业务代码使用，暂时没有作用
    private int code;
    private String name;
    private String desc;
    private MqQueueEnum(int code, String name, String desc){
        this.code = code;
        this.name = name;
        this.desc = desc;
    }
}
