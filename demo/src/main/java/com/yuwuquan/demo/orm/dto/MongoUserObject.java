package com.yuwuquan.demo.orm.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "userList")//集合名，后面对这个操作可以不带集合名
public class MongoUserObject {
//    @Indexed(unique=true)//唯一索引，看情况是否要添加
//    @Field("id")
//    private Long id = 0L;
    @Field("name")
    private String name;
    @Field("address")
    private String address;

    public MongoUserObject(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
