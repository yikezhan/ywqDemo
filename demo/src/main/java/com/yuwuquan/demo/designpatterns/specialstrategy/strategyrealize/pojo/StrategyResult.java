package com.yuwuquan.demo.designpatterns.specialstrategy.strategyrealize.pojo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 策略执行结果。在StrategyContextResult中。暂时不需要用到。
 */
@Deprecated
public class StrategyResult {
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private Integer id;

    /**
     * 策略上下文记录id
     */
    @Getter
    @Setter
    private Integer strategyContextResultId;

    /**
     * 策略类路径
     */
    @Getter
    @Setter
    private String clazz;

    /**
     * 策略执行结果状态
     */
    @Getter
    @Setter
    private Integer status;

    /**
     * 策略最近一次重试时间
     */
    @Getter
    @Setter
    private Date retryDate;

    /**
     * 策略最近一次失败原因
     */
    @Getter
    @Setter
    private Exception exception;

    /**
     * 策略最近一次失败原因
     */
    @Getter
    @Setter
    private String exceptionRecord;
}
