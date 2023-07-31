package com.learn.schedul;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.parsing.ImportDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Set;

@Component
@Conditional(com.learn.schedul.TaskTest.class)
public class SchedulScan extends ClassPathBeanDefinitionScanner {

//    @Value("")
//    private String name;

    public SchedulScan(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @PostConstruct
    void init(){

    }

    @Override
    protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
        //return super.isCandidateComponent(metadataReader);
        return true;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        //return super.isCandidateComponent(beanDefinition);
        boolean anInterface = beanDefinition.getMetadata().isInterface();
        return anInterface;
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        for (BeanDefinitionHolder holder :
                beanDefinitionHolders) {
            System.out.println(holder);
        }
        return null;
    }
}
