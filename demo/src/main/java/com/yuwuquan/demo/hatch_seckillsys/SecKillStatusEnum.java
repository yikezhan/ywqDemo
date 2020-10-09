package com.yuwuquan.demo.hatch_seckillsys;

import lombok.Getter;

@Getter
public enum SecKillStatusEnum {
    SecKillDoing(0,"0","秒杀中"),
    SecKillSuccess(1,"1","秒杀成功"),
    SecKillFail(2,"2","秒杀失败"),
    ;
    private Integer code;
    private String value;
    private String desc;

    SecKillStatusEnum(Integer code, String value, String desc) {
        this.code = code;
        this.value = value;
        this.desc = desc;
    }

    public static SecKillStatusEnum getEnumByValue(String val){
        for(SecKillStatusEnum secKillStatusEnum : SecKillStatusEnum.values()){
            if(secKillStatusEnum.value.equals(val)){
                return secKillStatusEnum;
            }
        }
        return null;
    }
}
