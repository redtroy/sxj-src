package com.sxj.mybatis.shard.datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import com.sxj.mybatis.shard.configuration.XmlReader;
import com.sxj.mybatis.shard.configuration.node.DataNodeCfg;
import com.sxj.mybatis.shard.configuration.node.KeyNodeCfg;
import com.sxj.mybatis.shard.configuration.node.ShardRuleCfg;

public class DataSourceFactory
{
    
    private static List<DataSourceNode> nodes;
    
    private static List<DataSource> keyGeneratorDs;
    
    private static Map<String, String> shardTables = new HashMap<String, String>();
    
    private static ApplicationContext context;
    
    public static void main(String[] args)
    {
        initDataSources();
    }
    
    public static List<DataSourceNode> getNodes()
    {
        if (nodes == null)
        {
            initDataSources();
        }
        return nodes;
    }
    
    public static List<DataSourceNode> getNodes(String tableName, String command)
    {
        if (nodes == null)
            initDataSources();
        return filter(tableName, command);
    }
    
    public static List<DataSourceNode> getNodes(String tableName)
    {
        if (nodes == null)
            initDataSources();
        return filter(tableName, null);
    }
    
    private static List<DataSourceNode> filter(String tableName, String command)
    {
        if (StringUtils.isEmpty(tableName))
            return nodes;
        List<DataSourceNode> result = new ArrayList<DataSourceNode>();
        for (DataSourceNode node : nodes)
        {
            if (node.getTables()
                    .toLowerCase()
                    .contains(tableName.toLowerCase()))
                result.add(node);
        }
        return result;
    }
    
    public static Map<String, String> getShardTables()
    {
        if (shardTables == null)
        {
            initDataSources();
        }
        return shardTables;
    }
    
    public static void initDataSources()
    {
        XmlReader.loadShardConfigs();
        //        Map<String, DataSourceCfg> dataSourceCfgs = XmlReader.getDataSources();
        List<DataNodeCfg> dataNodeCfgs = XmlReader.getDataNodes();
        Map<String, ShardRuleCfg> ruleCfgs = XmlReader.getRules();
        KeyNodeCfg keyNodeCfg = XmlReader.getKeyNodeCfg();
        try
        {
            //            Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
            //            for (Map.Entry<String, DataSourceCfg> entry : dataSourceCfgs.entrySet())
            //            {
            //                Properties p = new Properties();
            //                p.setProperty("driverClassName", "com.mysql.jdbc.Driver");
            //                p.setProperty("url", entry.getValue().getUrl());
            //                p.setProperty("username", entry.getValue().getUserName());
            //                p.setProperty("password", entry.getValue().getPassword());
            //                
            //                p.setProperty("maxActive", "1");
            //                p.setProperty("maxIdle", "1");
            //                p.setProperty("minIdle", "1");
            //                
            //                DataSource ds = BasicDataSourceFactory.createDataSource(p);
            //                
            //                dataSourceMap.put(entry.getValue().getName(), ds);
            //            }
            
            nodes = new ArrayList<DataSourceNode>();
            keyGeneratorDs = new ArrayList<DataSource>();
            List<String> kStr = split(keyNodeCfg.getKeyNodes(), ",");
            for (String str : kStr)
            {
                keyGeneratorDs.add(context.getBean(str, DataSource.class));
            }
            
            for (DataNodeCfg cfg : dataNodeCfgs)
            {
                List<DataSource> w = new ArrayList<DataSource>();
                List<DataSource> r = new ArrayList<DataSource>();
                
                List<String> wStr = split(cfg.getWriteNodes(), ",");
                for (String str : wStr)
                {
                    w.add(context.getBean(str, DataSource.class));
                }
                List<String> rStr = split(cfg.getReadNodes(), ",");
                for (String str : rStr)
                {
                    r.add(context.getBean(str, DataSource.class));
                }
                
                DataSourceNode dsNode = new DataSourceNode(w, r);
                dsNode.setTables(cfg.getTables());
                dsNode.setWriteTables(cfg.getWriteTables());
                dsNode.setReadTables(cfg.getReadTables());
                nodes.add(dsNode);
            }
            
            for (Map.Entry<String, ShardRuleCfg> entry : ruleCfgs.entrySet())
            {
                shardTables.put(entry.getKey(), entry.getValue().getColumn());
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private static List<String> split(String input, String s)
    {
        if (input == null)
        {
            return null;
        }
        List<String> list = new ArrayList<String>();
        String[] tmp = input.split(s);
        for (String str : tmp)
        {
            if (str == null || str.matches("\\s*"))
            {
                continue;
            }
            list.add(str.trim());
            
        }
        return list;
    }
    
    public DataSource getDefault()
    {
        return null;
    }
    
    public DataSource getByHash()
    {
        return null;
    }
    
    public static class DataSourceNode
    {
        
        private List<DataSource> writeNodes;
        
        private List<DataSource> readNodes;
        
        private String tables;
        
        private String writeTables;
        
        private String readTables;
        
        public DataSourceNode(List<DataSource> writeNodes,
                List<DataSource> readNodes)
        {
            this.writeNodes = writeNodes;
            this.readNodes = readNodes;
        }
        
        public List<DataSource> getWriteNodes()
        {
            return writeNodes;
        }
        
        public List<DataSource> getReadNodes()
        {
            return readNodes;
        }
        
        public String getTables()
        {
            return tables;
        }
        
        public void setTables(String tables)
        {
            this.tables = tables;
        }
        
        public String getWriteTables()
        {
            return writeTables;
        }
        
        public void setWriteTables(String writeTables)
        {
            this.writeTables = writeTables;
        }
        
        public void setWriteNodes(List<DataSource> writeNodes)
        {
            this.writeNodes = writeNodes;
        }
        
        public String getReadTables()
        {
            return readTables;
        }
        
        public void setReadTables(String readTables)
        {
            this.readTables = readTables;
        }
        
    }
    
    public static void setContext(ApplicationContext context)
    {
        DataSourceFactory.context = context;
    }
    
    public static List<DataSource> getKeyGeneratorDs()
    {
        return keyGeneratorDs;
    }
    
}
