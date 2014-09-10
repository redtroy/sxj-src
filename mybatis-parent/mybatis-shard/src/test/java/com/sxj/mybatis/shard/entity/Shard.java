package com.sxj.mybatis.shard.entity;

public class Shard
{
    private int shardId;
    
    private String shardName;
    
    public int getShardId()
    {
        return shardId;
    }
    
    public void setShardId(int shardId)
    {
        this.shardId = shardId;
    }
    
    public String getShardName()
    {
        return shardName;
    }
    
    public void setShardName(String shardName)
    {
        this.shardName = shardName;
    }
}
