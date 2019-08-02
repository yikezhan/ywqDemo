package com.yuwuquan.demo.service;

import com.yuwuquan.demo.orm.dto.MongoUserObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoRepositoryInter extends MongoRepository<MongoUserObject,String> {
    //方法名写时有提示，会自动有。且该接口不需要实现即可使用。
    MongoUserObject findMongoUserObjectsByNameEquals(String name);
}
