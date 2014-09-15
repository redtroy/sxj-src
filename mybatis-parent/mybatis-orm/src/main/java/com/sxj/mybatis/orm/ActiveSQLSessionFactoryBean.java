/*
 * @(#)ActiveSQLSessionFactoryBean.java 2013年12月27日 下午23:33:33
 *
 * Copyright (c) 2011-2013 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package com.sxj.mybatis.orm;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.builder.GenericStatementBuilder;
import com.sxj.spring.modules.util.Reflections;

/**
 * Class description goes here.
 */
public class ActiveSQLSessionFactoryBean extends SqlSessionFactoryBean
        implements ApplicationContextAware
{
    
    private Configuration configuration;
    
    private ResourceLoader resourceLoader;
    
    private ApplicationContext applicationContext;
    
    public static int times = 0;
    
    @Override
    public void afterPropertiesSet() throws Exception
    {
        System.out.println("------------------------------------------------------------"
                + times++);
        super.afterPropertiesSet();
        SqlSessionFactory sqlSessionFactory = super.getObject();
        configuration = sqlSessionFactory.getConfiguration();
        configuration.setAutoMappingBehavior(AutoMappingBehavior.NONE);
        for (String clazzName : findEntityClassNames())
        {
            GenericStatementBuilder builder = new GenericStatementBuilder(
                    configuration, Class.forName(clazzName));
            builder.build();
        }
    }
    
    @Override
    public SqlSessionFactory getObject() throws Exception
    {
        return super.getObject();
    }
    
    private Set<String> findEntityClassNames() throws IOException
    {
        Set<String> classNames = new HashSet<String>();
        SimpleMetadataReaderFactory metadataReaderFactory = new SimpleMetadataReaderFactory(
                applicationContext);
        String fieldValue = (String) Reflections.getFieldValue(this,
                "typeAliasesPackage");
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
                    String entityAnnotation = Entity.class.getName();
                    if (annotationMetadata.isAnnotated(entityAnnotation))
                    {
                        classNames.add(classMetadata.getClassName());
                    }
                }
            }
        }
        
        return classNames;
    }
    
    private URL[] findClassPath() throws IOException
    {
        ResourcePatternResolver resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        String fieldValue = (String) Reflections.getFieldValue(this,
                "typeAliasesPackage");
        Resource[] resources = resourcePatternResolver.getResources("classpath:"
                + StringUtils.replaceChars(fieldValue, '.', '/')
                + "/**/*.class");
        URL[] classPaths = new URL[resources.length];
        for (int i = 0; i < resources.length; i++)
            classPaths[i] = resources[i].getURL();
        return classPaths;
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
        this.applicationContext = applicationContext;
    }
    
}
