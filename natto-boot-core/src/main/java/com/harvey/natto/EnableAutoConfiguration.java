package com.harvey.natto;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author harvey
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {
    String ENABLED_OVERRIDE_PROPERTY = "spring.boot.enableautoconfiguration";
}
