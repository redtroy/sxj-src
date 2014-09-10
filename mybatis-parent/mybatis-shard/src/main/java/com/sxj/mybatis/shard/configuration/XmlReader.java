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
import com.sxj.mybatis.shard.configuration.node.ShardRuleCfg;

public class XmlReader
{
    
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
    
    public static List<DataNodeCfg> getDataNodes()
    {
        return dataNodes;
    }
    
    public static Map<String, ShardRuleCfg> getRules()
    {
        return rules;
    }
    
}
