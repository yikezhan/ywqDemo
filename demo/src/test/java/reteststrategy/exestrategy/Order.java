package reteststrategy.exestrategy;

import reteststrategy.StrategyContext;
import reteststrategy.Strategys;
import reteststrategy.strategy.StrategyOne;

@Strategys(strategys ={
        @StrategyContext(strategy = StrategyOne.class,order = 1,isSyn = true),
        @StrategyContext(strategy = StrategyOne.class,order = 2,isSyn = true)
})
public class Order extends ExeStrategy{
    @Override
    public void method() {
        exe();
    }
}
