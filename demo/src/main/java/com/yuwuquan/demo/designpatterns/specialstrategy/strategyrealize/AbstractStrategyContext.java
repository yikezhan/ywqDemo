package com.yuwuquan.demo.designpatterns.specialstrategy.strategyrealize;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yuwuquan.demo.config.MqMultiThreadConfig;
import com.yuwuquan.demo.designpatterns.specialstrategy.StrategyObjectSupplementInter;
import com.yuwuquan.demo.exception.ApplicationException;
import com.yuwuquan.demo.designpatterns.specialstrategy.strategytype.IStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;

/**
 * 执行实际策略类
 * @param <P>
 */
@Slf4j
public abstract class AbstractStrategyContext<P> implements StrategyContext<P> {

    //暂且用mq的连接池
    @Resource(name = MqMultiThreadConfig.BEANNAME)
    protected ExecutorService executorService;

    /**
     * 同步策略
     */
    protected final TreeMap<Integer, IStrategy> strategyMap = new TreeMap<Integer, IStrategy>();

    /**
     * 异步策略
     */
    protected final List<IStrategy> asynchronousStrategys = new ArrayList<>();

    @Override
    public void addStrategy(int key, IStrategy s) {
        strategyMap.put(key, s);
    }
    @Override
    public void addAsynchronousStrategy(IStrategy s) {
        asynchronousStrategys.add(s);
    }
    /**
     * 执行上下文所包含的策略
     */
    protected void execute(P p, StrategyObjectSupplementInter strategyObjectSupplementService) {
        String clazz = this.getClass().toString();
        log.info("策略上下文执行开始:::" + clazz);
        executeSychronousStretegy(p, strategyMap);
        //executeAsychronousStretegy(p,asynchronousStrategys);
        log.info("策略上下文执行结束:::" + clazz);
    }
    /**
     * 执行同步策略集合
     */
    @Transactional
    protected void executeSychronousStretegy(P p, TreeMap<Integer, IStrategy> strategyMap) {
        if (strategyMap != null && !strategyMap.isEmpty()) {
            for (Map.Entry<Integer, IStrategy> strategy : strategyMap.entrySet()) {
                IStrategy iStrategy = strategy.getValue();
                if (iStrategy != null) {
                    doExecuteStretegy(iStrategy, p);
                }
            }
        }
    }
    /**
     * 执行异步步策略集合
     */
    protected void executeAsychronousStretegy(P o, List<IStrategy> asynchronousStrategys) {
        if (CollectionUtils.isNotEmpty(asynchronousStrategys)) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for (IStrategy strategy : asynchronousStrategys) {
                        if (strategy != null) {
                            doExecuteStretegy(strategy, o);
                        }
                    }
                }
            });
        }
    }

    /**
     * 执行策略
     */
    public final void doExecuteStretegy(IStrategy abstractStrategy, P p) {
        if (abstractStrategy != null) {
            try {
                IStrategy<P> iStrategy = (IStrategy) abstractStrategy;
                if (iStrategy.check(p)) {
                    long startTime = System.currentTimeMillis();
                    iStrategy.process(p);
                    long executionTime = System.currentTimeMillis() - startTime;
                    log.info("策略执行时间统计:::" + iStrategy.getClass() +
                            "执行时间为: " + executionTime + "____________________________");
                }
            } catch (Exception e) {
                if (e instanceof ApplicationException) {
                    ApplicationException applicationException = (ApplicationException) e;
                    log.error(applicationException.getErrorMsg());
                } else {
                    log.info("Strategy Exception" + e.getMessage(), e);
                }
                throw e;
            }
        }
    }


}
