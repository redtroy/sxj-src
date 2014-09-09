/*
 *    Copyright 2010-2011 The myBatis Team
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.sxj.mybatis.shard.util;

import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionHolder;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import com.sxj.mybatis.shard.session.ShardSqlSessionFactory;
import com.sxj.mybatis.shard.transaction.ShardDataSourceTrasactionManager;


/**
 * Handles MyBatis SqlSession life cycle. It can register and get SqlSessions
 * from Spring {@code TransactionSynchronizationManager}. Also works if no
 * transaction is active.
 * 
 * @version $Id: SqlSessionUtils.java 4198 2011-12-02 08:20:30Z
 *          eduardo.macarron@gmail.com $
 */
public final class SqlSessionUtils {

	private static final Log logger = LogFactory.getLog(PreparedStatement.class);

	/**
	 * This class can't be instantiated, exposes static utility methods only.
	 */
	private SqlSessionUtils() {
		// do nothing
	}

	public static SqlSession getSqlSession(DataSource ds, ShardSqlSessionFactory sessionFactory, ExecutorType executorType, PersistenceExceptionTranslator exceptionTranslator) {

		SqlSession sess = ShardDataSourceTrasactionManager.getSession(ds);
		if (sess == null) {
			sess = sessionFactory.openSession(ds);
			ShardDataSourceTrasactionManager.putSession(ds, sess);
		}
		return sess;
	}

	public static void closeSqlSession(SqlSession session, DataSource ds) {
		SqlSession sess = ShardDataSourceTrasactionManager.getSession(ds);
		if (sess == session) {
			if (!TransactionSynchronizationManager.isSynchronizationActive()) {
				sess.close();
			}
		}
	}

}
