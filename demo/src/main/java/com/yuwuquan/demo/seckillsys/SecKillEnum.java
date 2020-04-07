package com.yuwuquan.demo.seckillsys;

import lombok.Getter;

@Getter
public enum SecKillEnum {
    SEC_KILL_START_KEY(1,"sec_kill_start_key","秒杀系统开始才能获取到的key",60*3),
    ;
    private Integer code;
    private String value;
    private String desc;
    private long time;//秒为单位

    SecKillEnum(Integer code, String value, String desc, long time) {
        this.code = code;
        this.value = value;
        this.desc = desc;
        this.time = time;
    }
}
