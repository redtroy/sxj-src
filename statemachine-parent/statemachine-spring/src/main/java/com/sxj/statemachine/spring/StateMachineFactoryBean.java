package com.sxj.statemachine.spring;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.util.Assert;

import com.sxj.statemachine.StateMachineFactory;
import com.sxj.statemachine.annotations.StateMachine;
import com.sxj.statemachine.exceptions.StateMachineException;

public class StateMachineFactoryBean implements
        ApplicationListener<ContextRefreshedEvent>, BeanFactoryPostProcessor,
        ApplicationContextAware
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(StateMachineFactoryBean.class);
    
    private ApplicationContext applicationContext = null;
    
    private String basePackages;
    
    private Set<String> findStateMachineConfigs() throws IOException
    {
        Set<String> classNames = new HashSet<String>();
        SimpleMetadataReaderFactory metadataReaderFactory = new SimpleMetadataReaderFactory(
                applicationContext);
        String fieldValue = getBasePackages();
        fieldValue = fieldValue == null ? "" : fieldValue;
        String[] split = fieldValue.split(",");
        for (String value : split)
        {
            Resource[] resources = applicationContext.getResources("classpath*:"
                    + StringUtils.replaceChars(value, '.', '/') + "/**/*.class");
            for (Resource resource : resources)
            {
                if (resource.isReadable())
                {
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    ClassMetadata classMetadata = metadataReader.getClassMetadata();
                    AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
                    String entityAnnotation = StateMachine.class.getName();
                    if (annotationMetadata.isAnnotated(entityAnnotation))
                    {
                        classNames.add(classMetadata.getClassName());
                    }
                }
            }
        }
        return classNames;
    }
    
    private void registerStateMachines(DefaultListableBeanFactory dlbf)
            throws Exception
    {
        
        Set<String> findStateMachineConfigs = findStateMachineConfigs();
        for (String className : findStateMachineConfigs)
        {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(StateMachineFactory.class);
            builder.setFactoryMethod("newInstance");
            Class<?> clazz = Class.forName(className);
            String name = getStateMachineName(clazz);
            Object instance = clazz.newInstance();
            String wrappedBean = getWrappedBean(dlbf, clazz);
            if (wrappedBean != null)
            {
                builder.addConstructorArgValue(instance)
                        .addConstructorArgReference(wrappedBean);
            }
            else
            {
                builder.addConstructorArgValue(instance);
            }
            builder.setLazyInit(false);
            dlbf.registerBeanDefinition(name, builder.getBeanDefinition());
        }
    }
    
    private String getStateMachineName(Class<?> clazz)
    {
        StateMachine annotation = clazz.getAnnotation(StateMachine.class);
        String stateMachineName = annotation.name();
        return stateMachineName;
    }
    
    private String getWrappedBean(DefaultListableBeanFactory beanFactory,
            Class<?> requiredType)
    {
        try
        {
            String[] beanNamesForType = beanFactory.getBeanNamesForType(requiredType);
            Assert.notEmpty(beanNamesForType);
            if (beanNamesForType.length > 1)
                throw new StateMachineException(
                        "You can only define one statemachine with Class:"
                                + requiredType);
            return beanNamesForType[0];
        }
        catch (Exception e)
        {
            LOGGER.debug("", e);
            
        }
        return null;
    }
    
    private String getBasePackages()
    {
        return basePackages;
    }
    
    public void setBasePackages(String basePackages)
    {
        this.basePackages = basePackages;
    }
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        // TODO Auto-generated method stub
        this.applicationContext = event.getApplicationContext();
        
        AutowireCapableBeanFactory autowireCapableBeanFactory = event.getApplicationContext()
                .getAutowireCapableBeanFactory();
        try
        {
            //            registerStateMachines((DefaultListableBeanFactory) autowireCapableBeanFactory);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Override
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory) throws BeansException
    {
        try
        {
            registerStateMachines((DefaultListableBeanFactory) beanFactory);
        }
        catch (Exception e)
        {
            LOGGER.debug("", e);
        }
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
        this.applicationContext = applicationContext;
    }
}
