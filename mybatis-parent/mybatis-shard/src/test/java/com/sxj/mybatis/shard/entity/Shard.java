package com.sxj.mybatis.shard.entity;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.shard.dao.ShardMapper;

@Entity(mapper = ShardMapper.class)
@Table(name = "SHARD")
public class Shard
{
    @Id(column = "SHARD_ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private int shardId;
    
    @Column(name = "SHARD_NAME")
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
