package reteststrategy;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Strategys {
    public StrategyContext[] strategys() default {};
}
