package com.yuwuquan.demo.designpatterns.specialstrategy.strategyfactory;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yuwuquan.demo.designpatterns.specialstrategy.annotation.StrategyContent;
import com.yuwuquan.demo.designpatterns.specialstrategy.annotation.StrategyRelation;
import com.yuwuquan.demo.designpatterns.specialstrategy.strategyrealize.AbstractStrategyContext;
import com.yuwuquan.demo.designpatterns.specialstrategy.strategyrealize.StrategyContext;
import com.yuwuquan.demo.designpatterns.specialstrategy.strategytype.IStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 解析注解类。
 * 备注：在项目启动时，strategyContexts变量自动注入了数据。
 * 1、第一次执行时会执行initRelationMapping()方法。所有@StrategyRelation注解修饰过的类，会按照<对象，注解集合>映射到relationMapping对象中。
 * 2、registerStrategy()方法被afterPropertiesSet()调用。解析具体的注解，并将注解的策略类注入到父类的变量中。
 */
@Component
public class AbstractStrategyContextFactory {

    /**
     * 1、emptyList()这个方法主要目的就是返回一个不可变的列表，为一个静态内部类，
     * 使用这个方法作为返回值就不需要再创建一个新对象，
     * 可以减少内存开销。并且返回一个size为0的List，调用者不需要校验返回值是否为null。
     * 所以建议使用这个方法返回可能为空的List。
     * 2、注解标注list，获取所有继承了StrategyContext且实例化了的类。（即那些使用策略注解的业务类）
     */
    @Autowired(required=false)
    protected List<StrategyContext> strategyContexts= Collections.emptyList();

    //不直接注入map，是因为注入map时key为类名，这里需要获取到该类本身。
    protected final static Map<StrategyContext, List<StrategyContent>> relationMapping = new HashMap<>();
    /**
     * 提取出strategyContexts中每一个类上标注的注解，并放了对应的map中保存。
     */
    protected void initRelationMapping(){
        if(!CollectionUtils.isEmpty(strategyContexts)){
            for(StrategyContext strategyContext :strategyContexts){
                StrategyRelation strategyRelation = strategyContext.getClass().getAnnotation(StrategyRelation.class);
                if(strategyRelation != null){
                    StrategyContent[] strategys = strategyRelation.strategys();
                    List<StrategyContent> strategyList = Arrays.asList(strategys);
                    relationMapping.put(strategyContext, strategyList);
                }
            }
        }
    }

    public <S extends IStrategy> void registerStrategy(S strategy) {
        if(relationMapping.isEmpty()){
            initRelationMapping();
        }
        if(!relationMapping.isEmpty()){
            for(Map.Entry<StrategyContext, List<StrategyContent>> map : relationMapping.entrySet()){
                List<StrategyContent> strategyContents = map.getValue();
                if(CollectionUtils.isNotEmpty(strategyContents)){
                    for(StrategyContent strategyContent : strategyContents){
                        //getClass()限制了对象只能是同一个类，而instanceof却允许对象是同一个类或其子类。
                        if(strategyContent.strategy().equals(strategy.getClass())){
                            StrategyContext strategyContext = map.getKey();
                            if(strategyContext != null){
                                AbstractStrategyContext abstractStrategyContext = (AbstractStrategyContext)strategyContext;
                                if(strategyContent.isAsynchronous()){
                                    abstractStrategyContext.addAsynchronousStrategy(strategy);
                                }else {
                                    abstractStrategyContext.addStrategy(strategyContent.order(), strategy);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
