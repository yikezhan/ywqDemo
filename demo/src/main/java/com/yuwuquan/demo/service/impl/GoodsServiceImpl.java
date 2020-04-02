package com.yuwuquan.demo.service.impl;

import com.yuwuquan.demo.orm.dao.GoodsMapper;
import com.yuwuquan.demo.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;
    @Override
    @Transactional
    public int updateGoodsInventory(Integer num) {
        int count = goodsMapper.updateGoodsInventory(num);
        System.out.println("休息一会，做一些其他操作");
        return count;
    }
}
