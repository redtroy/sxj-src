package com.sxj.mybatis.shard.util;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.context.ApplicationContext;

import com.sxj.mybatis.shard.transaction.ShardManagedTransaction;
import com.sxj.mybatis.shard.transaction.ShardManagedTransactionFactory;


public class ConfigUtil {

	private static Configuration configuration;

	private static ApplicationContext applicationContext;

	public static void setApplicationContext(ApplicationContext applicationContext) {
		ConfigUtil.applicationContext = applicationContext;
	}

	public static Configuration getConfiguration() {
		if (configuration == null) {
			DataSource dataSource = applicationContext.getBean("ds1", DataSource.class);
			TransactionFactory transactionFactory = new ShardManagedTransactionFactory();
			Environment environment = new Environment("development", transactionFactory, dataSource);
			configuration = new Configuration(environment);
		}
		return configuration;
	}
}
