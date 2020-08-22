package com.yuwuquan.demo.service;

import com.yuwuquan.demo.orm.dao.UserMapper;
import com.yuwuquan.demo.orm.model.User;
import com.yuwuquan.demo.orm.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    List<User> queryAll();
    void  insertOne();
    void modifyById(User user);
    void testMulThreadTransactional();
    void mockTranscationException(int a);
}
