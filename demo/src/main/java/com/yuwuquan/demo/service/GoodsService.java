package com.yuwuquan.demo.service;

import com.yuwuquan.demo.orm.model.User;

import java.util.List;

public interface GoodsService {
    int updateGoodsInventory(Integer num);//扣减库存
}
