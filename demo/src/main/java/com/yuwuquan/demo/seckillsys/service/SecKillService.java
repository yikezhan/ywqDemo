package com.yuwuquan.demo.seckillsys.service;

import com.yuwuquan.demo.seckillsys.dto.SecKillGoodsDTO;

public interface SecKillService {
    boolean inventoryDeduction(String sku);//扣库存
    int insertSecKillSuccessUser(SecKillGoodsDTO secKillGoodsDTO);
}
