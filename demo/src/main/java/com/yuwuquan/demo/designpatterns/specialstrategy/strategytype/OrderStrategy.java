package com.yuwuquan.demo.designpatterns.specialstrategy.strategytype;

import com.yuwuquan.demo.designpatterns.specialstrategy.pojo.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderStrategy extends AbstractStrategy<Order> {

    @Override
    public boolean check(Order order) {
        return true;
    }

    @Override
    public void process(Order order) {
        order.setOrderId(createOrderId());
    }

    //生成订单号
    private String createOrderId(){
        return "ABC1234567890";
    }
}
