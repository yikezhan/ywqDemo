package com.yuwuquan.demo.seckillsys.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class SecKillGoodsDTO implements Serializable {
    private static final long serialVersionUID = 668543345682101751L;

    Long userId;//秒杀用户id
    String sku;//秒杀商品sku
    String key;//活动开始时间key值
}
