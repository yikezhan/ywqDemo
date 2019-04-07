package com.yuwuquan.demo.service;

import com.yuwuquan.demo.orm.dao.UserMapper;
import com.yuwuquan.demo.orm.model.User;
import com.yuwuquan.demo.orm.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    public List<User>  getAll(){
        UserExample example = new UserExample();
        return userMapper.selectByExample(example);
    }
}
