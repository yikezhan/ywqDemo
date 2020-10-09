package com.yuwuquan.demo.service.impl;

import com.yuwuquan.demo.orm.dao.GoodsMapper;
import com.yuwuquan.demo.service.GoodsService;
import com.yuwuquan.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);
    @Override
    @Transactional
    public int updateGoodsInventory(Integer num) {
        int count = goodsMapper.updateGoodsInventory(num);
        System.out.println("休息一会，做一些其他操作");
        return count;
    }

    @Override
    @Transactional
    public int mockTranscationException(Integer num) {
        logger.error("1111111111111111111111111111");
        try {
            userService.mockTranscationException(num);
        }catch (Exception e){
            e.printStackTrace();
        }
        logger.error("22222222222222222222222222");
        return 0;
    }
}
