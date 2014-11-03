// Copyright (C) 2013 - Will Glozer.  All rights reserved.

package com.sxj.redis.output;

import com.sxj.redis.codec.RedisCodec;
import com.sxj.redis.protocol.CommandOutput;

import java.nio.ByteBuffer;

/**
 * Key output.
 *
 * @param <K> Key type.
 *
 * @author Will Glozer
 */
public class KeyOutput<K, V> extends CommandOutput<K, V, K>
{
    public KeyOutput(RedisCodec<K, V> codec)
    {
        super(codec, null);
    }
    
    @Override
    public void set(ByteBuffer bytes)
    {
        output = (bytes == null) ? null : codec.decodeKey(bytes);
    }
}
