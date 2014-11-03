// Copyright (C) 2011 - Will Glozer.  All rights reserved.

package com.sxj.redis.output;

import com.sxj.redis.codec.RedisCodec;
import com.sxj.redis.protocol.CommandOutput;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link List} of keys output.
 *
 * @param <K> Key type.
 *
 * @author Will Glozer
 */
public class KeyListOutput<K, V> extends CommandOutput<K, V, List<K>>
{
    public KeyListOutput(RedisCodec<K, V> codec)
    {
        super(codec, new ArrayList<K>());
    }
    
    @Override
    public void set(ByteBuffer bytes)
    {
        output.add(codec.decodeKey(bytes));
    }
}
