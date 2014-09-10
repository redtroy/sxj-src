package com.sxj.mybatis.shard.configuration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sxj.mybatis.shard.configuration.node.DataNodeCfg;
import com.sxj.mybatis.shard.configuration.node.DataSourceCfg;
import com.sxj.mybatis.shard.configuration.node.ShardRuleCfg;

public class XmlReader
{
    
    private static Map<String, DataSourceCfg> dataSources = new ConcurrentHashMap<String, DataSourceCfg>();
    
    private static List<DataNodeCfg> dataNodes = new ArrayList<DataNodeCfg>();
    
    private static Map<String, ShardRuleCfg> rules = new ConcurrentHashMap<String, ShardRuleCfg>();
    
    public static void loadShardConfigs()
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Element root = null;
        try
        {
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder db = factory.newDocumentBuilder();
            InputStream is = XmlReader.class.getResourceAsStream("/shard-config.xml");
            Document xmldoc = db.parse(is);
            root = xmldoc.getDocumentElement();
            NodeList nodeList = root.getElementsByTagName("dataSource");
            main: for (int i = 0; i < nodeList.getLength(); i++)
            {
                DataSourceCfg dataSource = new DataSourceCfg();
                String dsName = nodeList.item(i)
                        .getAttributes()
                        .getNamedItem("name")
                        .getNodeValue();
                dataSource.setName(dsName);
                dataSources.put(dsName, dataSource);
                NodeList dsNodes = nodeList.item(i).getChildNodes();
                for (int j = 0; j < dsNodes.getLength(); j++)
                {
                    Node tmp = dsNodes.item(j);
                    if (tmp == null)
                    {
                        continue main;
                    }
                    if ("url".equals(tmp.getNodeName()))
                    {
                        dataSource.setUrl(tmp.getTextContent());
                        continue;
                    }
                    if ("userName".equals(tmp.getNodeName()))
                    {
                        dataSource.setUserName(tmp.getTextContent());
                        continue;
                    }
                    if ("password".equals(tmp.getNodeName()))
                    {
                        dataSource.setPassword(tmp.getTextContent());
                        continue;
                    }
                }
                
            }
            
            NodeList dataNodesCfg = root.getElementsByTagName("dataNodes")
                    .item(0)
                    .getChildNodes();
            for (int i = 0; i < dataNodesCfg.getLength(); i++)
            {
                if (dataNodesCfg.item(i) == null
                        || dataNodesCfg.item(i).getChildNodes().getLength() < 1)
                {
                    continue;
                }
                NodeList dn = dataNodesCfg.item(i).getChildNodes();
                DataNodeCfg dataNode = new DataNodeCfg();
                dataNodes.add(dataNode);
                for (int k = 0; k < dn.getLength(); k++)
                {
                    Node tmp = dn.item(k);
                    if (tmp == null)
                    {
                        continue;
                    }
                    if ("writeNodes".equals(tmp.getNodeName()))
                    {
                        dataNode.setWriteNodes(tmp.getTextContent());
                        continue;
                    }
                    if ("readNodes".equals(tmp.getNodeName()))
                    {
                        dataNode.setReadNodes(tmp.getTextContent());
                        continue;
                    }
                }
            }
            
            NodeList ruleCfgs = root.getElementsByTagName("rules")
                    .item(0)
                    .getChildNodes();
            for (int i = 0; i < ruleCfgs.getLength(); i++)
            {
                Node tmp = ruleCfgs.item(i);
                if (tmp.getAttributes() == null)
                {
                    continue;
                }
                String table = tmp.getAttributes()
                        .getNamedItem("name")
                        .getNodeValue()
                        .toLowerCase();
                String column = tmp.getAttributes()
                        .getNamedItem("column")
                        .getNodeValue()
                        .toLowerCase();
                
                if (table == null || table.matches("\\s*") || column == null
                        || column.matches("\\s*"))
                {
                    continue;
                }
                ShardRuleCfg rule = new ShardRuleCfg();
                rule.setTableName(table);
                rule.setColumn(column);
                rules.put(rule.getTableName(), rule);
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static Map<String, DataSourceCfg> getDataSources()
    {
        return dataSources;
    }
    
    public static List<DataNodeCfg> getDataNodes()
    {
        return dataNodes;
    }
    
    public static Map<String, ShardRuleCfg> getRules()
    {
        return rules;
    }
    
}
