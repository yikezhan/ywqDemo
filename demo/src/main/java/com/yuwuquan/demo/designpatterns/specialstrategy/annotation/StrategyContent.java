package com.yuwuquan.demo.designpatterns.specialstrategy.annotation;


import com.yuwuquan.demo.designpatterns.specialstrategy.strategytype.IStrategy;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StrategyContent {
    public Class<? extends IStrategy> strategy();
    public int order();
    public boolean isAsynchronous() default false;
}
