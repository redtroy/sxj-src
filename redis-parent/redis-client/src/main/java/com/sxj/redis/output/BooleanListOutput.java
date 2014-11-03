// Copyright (C) 2011 - Will Glozer.  All rights reserved.

package com.sxj.redis.output;

import com.sxj.redis.codec.RedisCodec;
import com.sxj.redis.protocol.CommandOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link java.util.List} of boolean output.
 *
 * @author Will Glozer
 */
public class BooleanListOutput<K, V> extends CommandOutput<K, V, List<Boolean>>
{
    public BooleanListOutput(RedisCodec<K, V> codec)
    {
        super(codec, new ArrayList<Boolean>());
    }
    
    @Override
    public void set(long integer)
    {
        output.add((integer == 1) ? Boolean.TRUE : Boolean.FALSE);
    }
}
