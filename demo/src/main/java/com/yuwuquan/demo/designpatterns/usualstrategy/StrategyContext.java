package com.yuwuquan.demo.designpatterns.usualstrategy;

/**
 * 策略上下文
 */
public class StrategyContext<P,D> {
    private IStrategy iStrategy;

    public StrategyContext(IStrategy iStrategy) {
        this.iStrategy = iStrategy;
    }

    /**
     * 执行策略
     * @param p
     * @return
     */
    protected Object doHandle(P p){
        if(iStrategy.check(p)){
            return iStrategy.process(p);
        }
        return null;
    }
}
