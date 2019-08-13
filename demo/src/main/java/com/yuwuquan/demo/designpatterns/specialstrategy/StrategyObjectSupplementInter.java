package com.yuwuquan.demo.designpatterns.specialstrategy;

import com.yuwuquan.demo.designpatterns.specialstrategy.strategyrealize.pojo.StrategyContextResult;

public interface StrategyObjectSupplementInter {
    /**
     * 补充额外信息。该方法只做开个口子，补充些额外信息。暂时不用。
     * @return
     */
    default void obtainObjSupplement(StrategyContextResult strategyContextResult){

    }
}
