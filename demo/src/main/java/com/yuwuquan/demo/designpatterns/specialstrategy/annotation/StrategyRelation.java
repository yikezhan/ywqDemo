package com.yuwuquan.demo.designpatterns.specialstrategy.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StrategyRelation {
    StrategyContent[] strategys() default {};
}
