package reteststrategy.exestrategy;

import com.yuwuquan.demo.config.UsualMultiThreadConfig;
import reteststrategy.strategy.Strategy;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

public abstract class ExeStrategy implements AbstractOrder{
    List<Strategy> asyList = Collections.EMPTY_LIST;
    List<Strategy> list = Collections.EMPTY_LIST;

    public void addAsyList(Strategy strategy){
        asyList.add(strategy);
    }
    public void addList(int order, Strategy strategy){
        list.add(strategy);
    }
    @Resource(name = UsualMultiThreadConfig.BEANNAME)
    protected ExecutorService executorService;

    public void exe() {
        for(Strategy strategy: list){
            strategy.cal();
        }
        for(Strategy strategy: asyList){
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    strategy.cal();
                }
            });
        }
    }
}
