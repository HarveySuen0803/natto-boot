package com.harvey.natto;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 * @author harvey
 */
public class OnClassCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalOnClass.class.getName());
        if (attributes == null) {
            return false;
        }
        
        if (context.getClassLoader() == null) {
            return false;
        }
        
        String[] classNames = (String[]) attributes.get("value");
        for (String className : classNames) {
            try {
                context.getClassLoader().loadClass(className);
            } catch (ClassNotFoundException e) {
                return false;
            }
        }
        
        return true;
    }
}
