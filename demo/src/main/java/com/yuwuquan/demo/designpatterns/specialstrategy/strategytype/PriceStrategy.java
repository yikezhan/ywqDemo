package com.yuwuquan.demo.designpatterns.specialstrategy.strategytype;

import com.yuwuquan.demo.designpatterns.specialstrategy.pojo.Order;
import org.springframework.stereotype.Component;

@Component
public class PriceStrategy extends AbstractStrategy<Order> {
    @Override
    public boolean check(Order order) {
        return true;
    }

    @Override
    public void process(Order order) {
        order.setPrice(getPrice());
    }

    private Double getPrice(){
        return 100.0;
    }
}
