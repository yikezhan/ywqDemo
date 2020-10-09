package com.yuwuquan.demo.hatch_seckillsys.service;

import com.yuwuquan.demo.config.DozerConfig;
import com.yuwuquan.demo.orm.dao.SecKillMapper;
import com.yuwuquan.demo.hatch_seckillsys.dto.SecKillGoodsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecKillServiceImpl implements SecKillService{

    @Autowired
    private SecKillMapper secKillMapper;

    @Autowired
    private DozerConfig.DozerFactory factory;

    private static final Logger logger = LoggerFactory.getLogger(SecKillServiceImpl.class);
    @Override
    public boolean inventoryDeduction(String sku) {
        if(sku != null){
            try{
                int count = secKillMapper.inventoryDeduction(sku);
                if(count > 0 ){
                    return true;
                }else{
                    return false;
                }
            }catch (Exception e){
                logger.error("扣减库存异常，sku={}",sku);
                return false;
            }
        }
        return false;
    }

    @Override
    public int insertSecKillSuccessUser(SecKillGoodsDTO secKillGoodsDTO) {
        secKillMapper.insertSecKillSuccessUser(secKillGoodsDTO);
        return 0;
    }

}
