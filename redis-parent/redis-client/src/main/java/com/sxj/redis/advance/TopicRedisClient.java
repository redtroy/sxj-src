package com.sxj.redis.advance;

import com.sxj.redis.advance.core.RTopic;

public interface TopicRedisClient
{
    /**
     * Returns topic instance by name.
     *
     * @param name of the distributed topic
     * @return
     */
    <M> RTopic<M> getTopic(String name);
}
