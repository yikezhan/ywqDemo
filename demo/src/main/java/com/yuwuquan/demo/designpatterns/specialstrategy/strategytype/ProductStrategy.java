package com.yuwuquan.demo.designpatterns.specialstrategy.strategytype;

import com.yuwuquan.demo.designpatterns.specialstrategy.pojo.Order;
import org.springframework.stereotype.Component;

@Component
public class ProductStrategy extends AbstractStrategy<Order> {

    @Override
    public boolean check(Order order) {
        if(getNum()>0){
            return true;
        }
        return false;
    }

    @Override
    public void process(Order order) {
        order.setCanSale(true);
    }

    //获取产品剩余库存
    private Integer getNum(){
        return 100;
    }
}