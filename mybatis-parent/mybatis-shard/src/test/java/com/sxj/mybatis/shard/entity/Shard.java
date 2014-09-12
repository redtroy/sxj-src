package com.sxj.mybatis.shard.entity;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.shard.dao.ShardMapper;

@Entity(mapper = ShardMapper.class)
@Table(name = "SHARD")
public class Shard
{
    @Id(column = "SHARD_ID")
    @GeneratedValue
    private long shardId;
    
    @Column(name = "SHARD_NAME")
    private String shardName;
    
    public String getShardName()
    {
        return shardName;
    }
    
    public void setShardName(String shardName)
    {
        this.shardName = shardName;
    }
    
    public long getShardId()
    {
        return shardId;
    }
    
    public void setShardId(long shardId)
    {
        this.shardId = shardId;
    }
}
