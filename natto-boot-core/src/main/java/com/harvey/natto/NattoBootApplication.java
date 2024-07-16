package com.harvey.natto;

import com.harvey.natto.web.WebServerConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author harvey
 */
@Documented
@ComponentScan
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(WebServerConfiguration.class)
public @interface NattoBootApplication {
}
