// Copyright (C) 2011 - Will Glozer.  All rights reserved.

package com.sxj.redis.output;

import com.sxj.redis.codec.RedisCodec;
import com.sxj.redis.protocol.CommandOutput;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link List} of string output.
 *
 * @author Will Glozer
 */
public class StringListOutput<K, V> extends CommandOutput<K, V, List<String>>
{
    public StringListOutput(RedisCodec<K, V> codec)
    {
        super(codec, new ArrayList<String>());
    }
    
    @Override
    public void set(ByteBuffer bytes)
    {
        output.add(bytes == null ? null : decodeAscii(bytes));
    }
}
