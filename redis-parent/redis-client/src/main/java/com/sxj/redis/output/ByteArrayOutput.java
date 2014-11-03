// Copyright (C) 2012 - Will Glozer.  All rights reserved.

package com.sxj.redis.output;

import com.sxj.redis.codec.RedisCodec;
import com.sxj.redis.protocol.CommandOutput;

import java.nio.ByteBuffer;

/**
 * Byte array output.
 *
 * @author Will Glozer
 */
public class ByteArrayOutput<K, V> extends CommandOutput<K, V, byte[]>
{
    public ByteArrayOutput(RedisCodec<K, V> codec)
    {
        super(codec, null);
    }
    
    @Override
    public void set(ByteBuffer bytes)
    {
        if (bytes != null)
        {
            output = new byte[bytes.remaining()];
            bytes.get(output);
        }
    }
}
