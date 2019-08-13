package com.yuwuquan.demo.designpatterns.specialstrategy.strategyrealize.pojo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 策略上下文执行结果。本方法中不需要用到。用于保存策略的执行日志，可入库，可以考虑在业务类和执行类之间再加一层，记录日志。
 */
@Deprecated
public class StrategyContextResult {
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private Integer id;

    /**
     * 上下文类路径
     */
    @Getter
    @Setter
    private String clazz;

    /**
     * 上下文操作对象类型
     */
    @Getter
    @Setter
    private String objType;

    /**
     * 上下文操作对象内容
     */
    @Getter
    @Setter
    private String objRecord;

    /**
     * 上下文操作对象标识
     */
    @Getter
    @Setter
    private String objMark;

    /**
     * 上下文执行状态
     */
    @Getter
    @Setter
    private Integer status;

    /**
     * 上下文对应策略执行状态
     */
    @Getter
    @Setter
    private List<StrategyResult> strategyResults;
}
