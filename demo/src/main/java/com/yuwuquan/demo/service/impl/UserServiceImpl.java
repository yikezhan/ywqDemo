package com.yuwuquan.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuwuquan.demo.config.UsualMultiThreadConfig;
import com.yuwuquan.demo.orm.dao.UserMapper;
import com.yuwuquan.demo.orm.model.User;
import com.yuwuquan.demo.orm.model.UserExample;
import com.yuwuquan.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Resource(name= UsualMultiThreadConfig.BEANNAME)
    private ExecutorService executorService;

    public List<User>  queryAll(){
        PageHelper.startPage(1, 1);
        UserExample example = new UserExample();
        //return userMapper.selectByExample(example);
        return userMapper.queryAll();
    }

    public void  insertOne(){
        userMapper.insertOne();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void mockTranscationException(int a){
            userMapper.insertOne();
            int b = 5/a;
            userMapper.insertOne();
    }
    /**
     * 结论：怎么说呢，多线程和事务可能天生就不可调和。
     * 在一个事务下启多个线程线程内的代码就不会被事务约束了。
     * spring 的事务是通过LocalThread来保证线程安全的，事务和当前线程绑定， 搞了多个线程自然会让事务失效。
     */
    @Transactional
    public void testMulThreadTransactional(){
        Runnable runnable = new Runnable() {
            private AtomicInteger sign = new AtomicInteger(0);
            @Override
            public void run() {
                testMulThread(sign.getAndIncrement());
            }
        };
        Thread thread = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        executorService.submit(thread);
        executorService.submit(thread2);
    }
    private void testMulThread(int sign){
        if(sign == 0){
            insertOne();
        }else{
            int a = 5/0;
        }
    }
    public void modifyById(User user){
        userMapper.updateByPrimaryKey(user);
    }
}
