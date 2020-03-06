package reteststrategy.strategy;

import reteststrategy.RegisterClass;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Strategy  implements InitializingBean,StrategyMethod{
    @Autowired
    RegisterClass registerClass;
    @Override
    public void afterPropertiesSet() throws Exception {
        registerClass.registerMap(this);
    }
}
