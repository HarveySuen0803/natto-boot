package com.harvey.natto;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.*;

/**
 * @author harvey
 */
public class AutoConfigurationImportSelector implements DeferredImportSelector, BeanClassLoaderAware, EnvironmentAware {
    private static final String[] NO_IMPORTS = {};
    
    private static final AutoConfigurationEntry EMPTY_ENTRY = new AutoConfigurationEntry();
    
    private ClassLoader beanClassLoader;
    
    private Environment environment;
    
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        if (isNotEnabled()) {
            return NO_IMPORTS;
        }
        
        return new String[0];
    }
    
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
    
    protected final Environment getEnvironment() {
        return this.environment;
    }
    
    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }
    
    protected final ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }
    
    protected final boolean isEnabled() {
        if (getClass() == AutoConfigurationImportSelector.class) {
            return getEnvironment().getProperty(EnableAutoConfiguration.ENABLED_OVERRIDE_PROPERTY, Boolean.class, true);
        }
        return true;
    }
    
    protected final boolean isNotEnabled() {
        return !isEnabled();
    }
    
    /**
     * Return the source annotation class used by the selector.
     * @return the annotation class
     */
    protected Class<?> getAnnotationClass() {
        return EnableAutoConfiguration.class;
    }
    
    /**
     * Return the appropriate {@link AnnotationAttributes} from the
     * {@link AnnotationMetadata}. By default this method will return attributes for
     * {@link #getAnnotationClass()}.
     * @param metadata the annotation metadata
     * @return annotation attributes
     */
    protected AnnotationAttributes getAttributes(AnnotationMetadata metadata) {
        String name = getAnnotationClass().getName();
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(name, true));
        Assert.notNull(attributes, () -> "No auto-configuration attributes found. Is " + metadata.getClassName()
            + " annotated with " + ClassUtils.getShortName(name) + "?");
        return attributes;
    }
    
    /**
     * Return the {@link AutoConfigurationEntry} based on the {@link AnnotationMetadata}
     * of the importing {@link Configuration @Configuration} class.
     * @param annotationMetadata the annotation metadata of the configuration class
     * @return the auto-configurations that should be imported
     */
    protected AutoConfigurationEntry getAutoConfigurationEntry(AnnotationMetadata annotationMetadata) {
        if (isNotEnabled()) {
            return EMPTY_ENTRY;
        }
        AnnotationAttributes attributes = getAttributes(annotationMetadata);
        List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);
        return new AutoConfigurationEntry(configurations, null);
    }
    
    /**
     * Return the auto-configuration class names that should be considered. By default,
     * this method will load candidates using {@link ImportCandidates}.
     * @param metadata the source metadata
     * @param attributes the {@link #getAttributes(AnnotationMetadata) annotation
     * attributes}
     * @return a list of candidate configurations
     */
    protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
        List<String> configurations = ImportCandidates.load(AutoConfiguration.class, getBeanClassLoader())
            .getCandidates();
        Assert.notEmpty(configurations,
            "No auto configuration classes found in "
                + "META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports. If you "
                + "are using a custom packaging, make sure that file is correct.");
        return configurations;
    }
    
    protected static class AutoConfigurationEntry {
        private final List<String> configurations;
        
        private final Set<String> exclusions;
        
        private AutoConfigurationEntry() {
            this.configurations = Collections.emptyList();
            this.exclusions = Collections.emptySet();
        }
        
        /**
         * Create an entry with the configurations that were contributed and their
         * exclusions.
         * @param configurations the configurations that should be imported
         * @param exclusions the exclusions that were applied to the original list
         */
        AutoConfigurationEntry(Collection<String> configurations, Collection<String> exclusions) {
            this.configurations = new ArrayList<>(configurations);
            this.exclusions = new HashSet<>(exclusions);
        }
        
        public List<String> getConfigurations() {
            return this.configurations;
        }
        
        public Set<String> getExclusions() {
            return this.exclusions;
        }
    }
}
