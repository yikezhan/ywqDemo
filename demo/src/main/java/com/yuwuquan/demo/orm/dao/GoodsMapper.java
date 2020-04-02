package com.yuwuquan.demo.orm.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsMapper {
    int updateGoodsInventory(Integer num);
}