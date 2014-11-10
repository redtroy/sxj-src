// Copyright (C) 2011 - Will Glozer.  All rights reserved.

package com.sxj.redis.output;

import com.sxj.redis.codec.RedisCodec;
import com.sxj.redis.protocol.CommandOutput;

import java.nio.ByteBuffer;

/**
 * Boolean output. The actual value is returned as an integer
 * where 0 indicates false and 1 indicates true, or as a null
 * bulk reply for script output.
 *
 * @author Will Glozer
 */
public class BooleanOutput<K, V> extends CommandOutput<K, V, Boolean>
{
    public BooleanOutput(RedisCodec<K, V> codec)
    {
        super(codec, null);
    }
    
    @Override
    public void set(long integer)
    {
        output = (integer == 1) ? Boolean.TRUE : Boolean.FALSE;
    }
    
    @Override
    public void set(ByteBuffer bytes)
    {
        output = (bytes != null) ? Boolean.TRUE : Boolean.FALSE;
    }
}
