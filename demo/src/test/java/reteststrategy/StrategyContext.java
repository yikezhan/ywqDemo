package reteststrategy;

import reteststrategy.strategy.Strategy;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StrategyContext {
    public Class<? extends Strategy> strategy();
    public int order();
    public boolean isSyn() default false;
}
