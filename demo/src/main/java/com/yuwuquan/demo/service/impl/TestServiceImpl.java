package com.yuwuquan.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuwuquan.demo.DemoApplication;
import com.yuwuquan.demo.config.UsualMultiThreadConfig;
import com.yuwuquan.demo.orm.dao.UserMapper;
import com.yuwuquan.demo.orm.model.User;
import com.yuwuquan.demo.orm.model.UserExample;
import com.yuwuquan.demo.service.GoodsService;
import com.yuwuquan.demo.service.TestService;
import com.yuwuquan.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @Transactional
    @Override
    public void mockTranscationException(){
        logger.error("aaaaaaaaaa");
        goodsService.mockTranscationException(0);
        logger.error("bbbbbbbbbb");
        goodsService.mockTranscationException(1);
    }
}
