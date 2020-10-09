package com.yuwuquan.demo.hatch_seckillsys.service;

import com.yuwuquan.demo.hatch_seckillsys.dto.SecKillGoodsDTO;

public interface SecKillService {
    boolean inventoryDeduction(String sku);//扣库存
    int insertSecKillSuccessUser(SecKillGoodsDTO secKillGoodsDTO);
}
