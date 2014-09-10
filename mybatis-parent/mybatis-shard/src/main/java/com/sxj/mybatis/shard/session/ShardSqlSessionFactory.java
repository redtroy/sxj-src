package com.sxj.mybatis.shard.session;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;

import com.sxj.mybatis.shard.MybatisConfiguration;


public class ShardSqlSessionFactory {

	private Configuration configuration;
	private TransactionFactory managedTransactionFactory;

	private static ShardSqlSessionFactory factory;

	private ShardSqlSessionFactory() {
	}

	public static ShardSqlSessionFactory instance() {
		if (factory == null) {
			factory = new ShardSqlSessionFactory();
			factory.configuration = MybatisConfiguration.getConfiguration();
			factory.managedTransactionFactory = new ManagedTransactionFactory();
		}
		return factory;
	}

	public SqlSession openSession(DataSource ds) {
		return openSessionFromDataSource(ds, configuration.getDefaultExecutorType(), null, false);
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	private SqlSession openSessionFromDataSource(DataSource ds, ExecutorType execType, TransactionIsolationLevel level, boolean autoCommit) {
		Transaction tx = null;
		try {
			final Environment environment = configuration.getEnvironment();
			final TransactionFactory transactionFactory = getTransactionFactoryFromEnvironment(environment);

			tx = transactionFactory.newTransaction(ds, level, autoCommit);

			final Executor executor = configuration.newExecutor(tx, execType);
			return new DefaultSqlSession(configuration, executor);
		} catch (Exception e) {
			closeTransaction(tx); // may have fetched a connection so lets call close()
			throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
		} finally {
			ErrorContext.instance().reset();
		}
	}

	private TransactionFactory getTransactionFactoryFromEnvironment(Environment environment) {
		if (environment == null || environment.getTransactionFactory() == null) {
			return managedTransactionFactory;
		}
		return environment.getTransactionFactory();
	}

	private void closeTransaction(Transaction tx) {
		if (tx != null) {
			try {
				tx.close();
			} catch (SQLException ignore) {
				// Intentionally ignore. Prefer previous error.
			}
		}
	}

}
