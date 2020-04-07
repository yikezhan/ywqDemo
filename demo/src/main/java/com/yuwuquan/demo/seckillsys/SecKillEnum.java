package com.yuwuquan.demo.seckillsys;

import com.yuwuquan.demo.util.common.DateUtil;
import lombok.Getter;

@Getter
public enum SecKillEnum {
    SEC_KILL_START_KEY(1,"sec_kill_start_key","秒杀系统开始才能获取到的key", DateUtil.getSBD(2)),
    SEC_KILL_QUEUE_NUM_PRE(1,"sec_kill_queue_num_pre_","秒杀商品（key前缀）在队列中的数目",DateUtil.getSBD(2)),
    SEC_KILL_QUEUE_USER_PRE(1,"sec_kill_queue_user_","进入队列的用户(key前缀)",DateUtil.getSBM(2)),
    ;
    private Integer code;
    private String key;
    private String desc;
    private long time;//秒为单位

    SecKillEnum(Integer code, String key, String desc, long time) {
        this.code = code;
        this.key = key;
        this.desc = desc;
        this.time = time;
    }
}
