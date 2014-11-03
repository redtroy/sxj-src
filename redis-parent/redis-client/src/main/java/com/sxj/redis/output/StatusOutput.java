// Copyright (C) 2011 - Will Glozer.  All rights reserved.

package com.sxj.redis.output;

import com.sxj.redis.codec.RedisCodec;
import com.sxj.redis.protocol.CommandOutput;

import java.nio.ByteBuffer;

import static com.sxj.redis.protocol.Charsets.buffer;

/**
 * Status message output.
 *
 * @author Will Glozer
 */
public class StatusOutput<K, V> extends CommandOutput<K, V, String>
{
    private static final ByteBuffer OK = buffer("OK");
    
    public StatusOutput(RedisCodec<K, V> codec)
    {
        super(codec, null);
    }
    
    @Override
    public void set(ByteBuffer bytes)
    {
        output = OK.equals(bytes) ? "OK" : decodeAscii(bytes);
    }
}
