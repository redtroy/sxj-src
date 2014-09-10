package com.sxj.mybatis.shard.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class ShardDataSourceTrasactionManager extends
        AbstractPlatformTransactionManager
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private static ThreadLocal<Map<DataSource, SqlSession>> currentSessions = new ThreadLocal<Map<DataSource, SqlSession>>();
    
    private static ThreadLocal<Map<DataSource, Connection>> currentConnections = new ThreadLocal<Map<DataSource, Connection>>();
    
    public static void putSession(DataSource ds, SqlSession sqlSession)
    {
        Map<DataSource, SqlSession> sessions = currentSessions.get();
        if (sessions == null)
        {
            sessions = new HashMap<DataSource, SqlSession>();
            currentSessions.set(sessions);
        }
        sessions.put(ds, sqlSession);
    }
    
    public static SqlSession getSession(DataSource ds)
    {
        Map<DataSource, SqlSession> sessions = currentSessions.get();
        if (sessions == null)
        {
            return null;
        }
        return sessions.get(ds);
    }
    
    public static void putConnection(DataSource ds, Connection conn)
    {
        Map<DataSource, Connection> conns = currentConnections.get();
        if (conns == null)
        {
            conns = new HashMap<DataSource, Connection>();
            currentConnections.set(conns);
        }
        conns.put(ds, conn);
    }
    
    public static Connection getConnection(DataSource ds)
    {
        Map<DataSource, Connection> conns = currentConnections.get();
        if (conns == null)
        {
            return null;
        }
        return conns.get(ds);
    }
    
    public static void closeConnection(DataSource ds)
    {
        Map<DataSource, Connection> conns = currentConnections.get();
        if (conns == null)
        {
            return;
        }
        Connection conn = conns.get(ds);
        conns.remove(ds);
        try
        {
            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    protected Object doGetTransaction() throws TransactionException
    {
        TransactionObject txObject = new TransactionObject();
        return txObject;
    }
    
    protected void doBegin(Object transaction, TransactionDefinition definition)
            throws TransactionException
    {
        
    }
    
    protected void doCommit(DefaultTransactionStatus status)
            throws TransactionException
    {
        
        Map<DataSource, Connection> conns = currentConnections.get();
        for (Map.Entry<DataSource, Connection> entry : conns.entrySet())
        {
            try
            {
                entry.getValue().commit();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        
        currentConnections.remove();
        currentSessions.remove();
    }
    
    protected void doRollback(DefaultTransactionStatus status)
            throws TransactionException
    {
        
        Map<DataSource, Connection> conns = currentConnections.get();
        for (Map.Entry<DataSource, Connection> entry : conns.entrySet())
        {
            try
            {
                entry.getValue().rollback();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        
        currentConnections.remove();
        currentSessions.remove();
    }
    
}
