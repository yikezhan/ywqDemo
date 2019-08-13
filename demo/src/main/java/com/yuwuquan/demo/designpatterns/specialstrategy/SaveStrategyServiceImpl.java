package com.yuwuquan.demo.designpatterns.specialstrategy;

import com.yuwuquan.demo.designpatterns.specialstrategy.annotation.StrategyContent;
import com.yuwuquan.demo.designpatterns.specialstrategy.annotation.StrategyRelation;
import com.yuwuquan.demo.designpatterns.specialstrategy.pojo.Order;
import com.yuwuquan.demo.designpatterns.specialstrategy.strategyrealize.AbstractStrategyContext;
import com.yuwuquan.demo.designpatterns.specialstrategy.strategyrealize.pojo.StrategyContextResult;
import com.yuwuquan.demo.designpatterns.specialstrategy.strategytype.PriceStrategy;
import com.yuwuquan.demo.designpatterns.specialstrategy.strategytype.ProductStrategy;
import com.yuwuquan.demo.designpatterns.specialstrategy.strategytype.OrderStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 公司中的策略模式。根据注解依次执行策略。该测试中，依次设置Order的参数。
 */
@Slf4j
@Service
@StrategyRelation(strategys = {
    @StrategyContent(strategy = OrderStrategy.class,order = 1),
    @StrategyContent(strategy = ProductStrategy.class,order = 2),
    @StrategyContent(strategy = PriceStrategy.class,order = 3)})
public class SaveStrategyServiceImpl extends AbstractStrategyContext<Order> implements SaveStrategyServiceInter<Order> {

    @Override
    public void save(Order order) {
        execute(order, new StrategyObjectSupplementInter() {
            @Override
            public void obtainObjSupplement(StrategyContextResult strategyContextResult) {
                strategyContextResult.setObjMark(order.getOrderId());
            }
        });
    }
}
