package reteststrategy;

import reteststrategy.exestrategy.AbstractOrder;
import reteststrategy.exestrategy.ExeStrategy;
import reteststrategy.strategy.Strategy;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RegisterClass{

    private final static HashMap<AbstractOrder,List<StrategyContext>> map = new HashMap<>();//所有的策略

    @Autowired(required=false) //注入所有实例化的类的对象
    protected List<AbstractOrder> abstractOrders;

    public void init(){
        for(AbstractOrder abstractOrder : abstractOrders) {
            //提取该注解塞进map
            Strategys strategys = abstractOrder.getClass().getAnnotation(Strategys.class);
            if(strategys != null){
                StrategyContext[] strategyContext = strategys.strategys();
                List<StrategyContext> strategyList = Arrays.asList(strategyContext);
                map.put(abstractOrder, strategyList);
            }
        }
    }

    public void registerMap(Strategy strategy){
        if(map.isEmpty()) init();
        if(!map.isEmpty()){
            for(Map.Entry<AbstractOrder, List<StrategyContext>> map : map.entrySet()){
                List<StrategyContext> strategyContents = map.getValue();
                if(CollectionUtils.isNotEmpty(strategyContents)){
                    for(StrategyContext strategyContext : strategyContents){
                        //getClass()限制了对象只能是同一个类，而instanceof却允许对象是同一个类或其子类。
                        if(strategyContext.strategy().equals(strategy.getClass())){
                            AbstractOrder abstractOrder = map.getKey();
                            if(strategyContext != null){
                                ExeStrategy exeStrategy = (ExeStrategy)abstractOrders;
                                if(strategyContext.isSyn()){
                                    exeStrategy.addAsyList(strategy);
                                }else {
                                    exeStrategy.addList(strategyContext.order(), strategy);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
