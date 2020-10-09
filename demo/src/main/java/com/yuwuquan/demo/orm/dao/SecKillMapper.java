package com.yuwuquan.demo.orm.dao;


import com.yuwuquan.demo.hatch_seckillsys.dto.SecKillGoodsDTO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface SecKillMapper {
    int inventoryDeduction(String sku);
    int insertSecKillSuccessUser(SecKillGoodsDTO secKillGoodsDTO);
}