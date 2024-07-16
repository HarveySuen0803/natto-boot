package com.harvey.natto;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author harvey
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Conditional({OnClassCondition.class})
public @interface ConditionalOnClass {
    String[] value() default "";
}
