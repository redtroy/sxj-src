// Copyright (C) 2011 - Will Glozer.  All rights reserved.

package com.sxj.redis.output;

import com.sxj.redis.codec.RedisCodec;
import com.sxj.redis.protocol.CommandOutput;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link List} of values output.
 *
 * @param <V> Value type.
 *
 * @author Will Glozer
 */
public class ValueListOutput<K, V> extends CommandOutput<K, V, List<V>>
{
    public ValueListOutput(RedisCodec<K, V> codec)
    {
        super(codec, new ArrayList<V>());
    }
    
    @Override
    public void set(ByteBuffer bytes)
    {
        output.add(bytes == null ? null : codec.decodeValue(bytes));
    }
}
