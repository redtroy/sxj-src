package com.sxj.mybatis.shard.dao;

import com.sxj.mybatis.shard.entity.Shard;

public interface ShardMapper
{
    public void insert(Shard shard);
    
    public Shard get(int shardId);
}
