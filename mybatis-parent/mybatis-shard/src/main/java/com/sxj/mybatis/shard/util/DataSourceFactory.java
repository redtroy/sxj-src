package com.sxj.mybatis.shard.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import com.sxj.mybatis.shard.configuration.XmlReader;
import com.sxj.mybatis.shard.configuration.node.DataNodeCfg;
import com.sxj.mybatis.shard.configuration.node.DataSourceCfg;
import com.sxj.mybatis.shard.configuration.node.ShardRuleCfg;


public class DataSourceFactory {

	private static List<DataSourceNode> nodes;

	private static Map<String, String> shardTables = new HashMap<String, String>();

	public static void main(String[] args) {
		initDataSources();
	}

	public static List<DataSourceNode> getNodes() {
		if (nodes == null) {
			initDataSources();
		}
		return nodes;
	}

	public static Map<String, String> getShardTables() {
		if (shardTables == null) {
			initDataSources();
		}
		return shardTables;
	}

	public static void initDataSources() {
		XmlReader.loadShardConfigs();
		Map<String, DataSourceCfg> dataSourceCfgs = XmlReader.getDataSources();
		List<DataNodeCfg> dataNodeCfgs = XmlReader.getDataNodes();
		Map<String, ShardRuleCfg> ruleCfgs = XmlReader.getRules();

		try {
			Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
			for (Map.Entry<String, DataSourceCfg> entry : dataSourceCfgs.entrySet()) {
				Properties p = new Properties();
				p.setProperty("driverClassName", "com.mysql.jdbc.Driver");
				p.setProperty("url", entry.getValue().getUrl());
				p.setProperty("username", entry.getValue().getUserName());
				p.setProperty("password", entry.getValue().getPassword());
				
				p.setProperty("maxActive", "1");
				p.setProperty("maxIdle", "1");
				p.setProperty("minIdle","1");
				
				
				
				DataSource ds = BasicDataSourceFactory.createDataSource(p);
				
				dataSourceMap.put(entry.getValue().getName(), ds);
			}

			nodes = new ArrayList<DataSourceNode>();

			for (DataNodeCfg cfg : dataNodeCfgs) {
				List<DataSource> w = new ArrayList<DataSource>();
				List<DataSource> r = new ArrayList<DataSource>();
				List<String> wStr = split(cfg.getWriteNodes(), ",");
				for (String str : wStr) {
					if (dataSourceMap.get(str) != null) {
						w.add(dataSourceMap.get(str));
					}
				}
				List<String> rStr = split(cfg.getReadNodes(), ",");
				for (String str : rStr) {
					if (dataSourceMap.get(str) != null) {
						r.add(dataSourceMap.get(str));
					}
				}

				DataSourceNode dsNode = new DataSourceNode(w, r);
				nodes.add(dsNode);
			}
			dataSourceMap = null;

			for (Map.Entry<String, ShardRuleCfg> entry : ruleCfgs.entrySet()) {
				shardTables.put(entry.getKey(), entry.getValue().getColumn());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static List<String> split(String input, String s) {
		if (input == null) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		String[] tmp = input.split(s);
		for (String str : tmp) {
			if (str == null || str.matches("\\s*")) {
				continue;
			}
			list.add(str.trim());

		}
		return list;
	}

	public DataSource getDefault() {
		return null;
	}

	public DataSource getByHash() {
		return null;
	}

	public static class DataSourceNode {

		private List<DataSource> writeNodes;

		private List<DataSource> readNodes;

		public DataSourceNode(List<DataSource> writeNodes, List<DataSource> readNodes) {
			this.writeNodes = writeNodes;
			this.readNodes = readNodes;
		}

		public List<DataSource> getWriteNodes() {
			return writeNodes;
		}

		public List<DataSource> getReadNodes() {
			return readNodes;
		}

	}
}
