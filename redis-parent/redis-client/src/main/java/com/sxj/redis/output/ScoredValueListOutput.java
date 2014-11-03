// Copyright (C) 2011 - Will Glozer.  All rights reserved.

package com.sxj.redis.output;

import com.sxj.redis.ScoredValue;
import com.sxj.redis.codec.RedisCodec;
import com.sxj.redis.protocol.CommandOutput;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link List} of values and their associated scores.
 *
 * @param <V> Value type.
 *
 * @author Will Glozer
 */
public class ScoredValueListOutput<K, V> extends
        CommandOutput<K, V, List<ScoredValue<V>>>
{
    private V value;
    
    public ScoredValueListOutput(RedisCodec<K, V> codec)
    {
        super(codec, new ArrayList<ScoredValue<V>>());
    }
    
    @Override
    public void set(ByteBuffer bytes)
    {
        if (value == null)
        {
            value = codec.decodeValue(bytes);
            return;
        }
        
        double score = Double.parseDouble(decodeAscii(bytes));
        output.add(new ScoredValue<V>(score, value));
        value = null;
    }
}
