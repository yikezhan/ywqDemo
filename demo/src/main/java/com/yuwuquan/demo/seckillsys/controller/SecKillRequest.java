package com.yuwuquan.demo.seckillsys.controller;

import com.yuwuquan.demo.seckillsys.dto.SecKillGoodsDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class SecKillRequest implements Serializable {
    private static final long serialVersionUID = 668543345682101751L;

    private SecKillGoodsDTO secKillGoodsDTO;//秒杀商品信息
}
