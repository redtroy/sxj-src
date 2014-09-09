package com.sxj.mybatis.shard.configuration.node;

public class DataNodeCfg {

	private String writeNodes;

	private String readNodes;

	
	
	@Override
	public String toString() {
		return "DataNode [writeNodes=" + writeNodes + ", readNodes=" + readNodes + "]";
	}

	public String getWriteNodes() {
		return writeNodes;
	}

	public void setWriteNodes(String writeNodes) {
		this.writeNodes = writeNodes;
	}

	public String getReadNodes() {
		return readNodes;
	}

	public void setReadNodes(String readNodes) {
		this.readNodes = readNodes;
	}

}
