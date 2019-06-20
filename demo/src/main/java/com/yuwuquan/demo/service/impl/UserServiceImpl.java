package com.yuwuquan.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuwuquan.demo.orm.dao.UserMapper;
import com.yuwuquan.demo.orm.model.User;
import com.yuwuquan.demo.orm.model.UserExample;
import com.yuwuquan.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User>  queryAll(){
        PageHelper.startPage(1, 1);
        UserExample example = new UserExample();
        //return userMapper.selectByExample(example);
        return userMapper.queryAll();
    }
}
