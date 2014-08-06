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
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.builder.GenericStatementBuilder;

/**
 * Class description goes here.
 */
public class ActiveSQLSessionFactoryBean extends SqlSessionFactoryBean {

	private Configuration configuration;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		SqlSessionFactory sqlSessionFactory = super.getObject();
		configuration = sqlSessionFactory.getConfiguration();

		for (String clazzName : findEntityClassNames()) {
			GenericStatementBuilder builder = new GenericStatementBuilder(configuration,
					Class.forName(clazzName));
			builder.build();
		}
	}

	@Override
	public SqlSessionFactory getObject() throws Exception {
		return super.getObject();
	}

	private Set<String> findEntityClassNames() throws IOException {
		AnnotationDB annotationDB = new AnnotationDB();
		annotationDB.setScanClassAnnotations(true);
		annotationDB.scanArchives(ClasspathUrlFinder.findClassPaths());
		Map<String, Set<String>> map = annotationDB.getAnnotationIndex();
		Set<String> classNames = map.get(Entity.class.getName());

		return classNames;
	}

}
