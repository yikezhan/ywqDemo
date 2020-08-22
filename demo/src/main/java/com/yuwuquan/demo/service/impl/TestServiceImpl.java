package com.yuwuquan.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuwuquan.demo.config.UsualMultiThreadConfig;
import com.yuwuquan.demo.orm.dao.UserMapper;
import com.yuwuquan.demo.orm.model.User;
import com.yuwuquan.demo.orm.model.UserExample;
import com.yuwuquan.demo.service.TestService;
import com.yuwuquan.demo.service.UserService;
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

    @Transactional
    @Override
    public void mockTranscationException(){
        userService.mockTranscationException(1);
        try {
            userService.mockTranscationException(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        userService.mockTranscationException(3);
    }
}
