// Copyright (C) 2011 - Will Glozer.  All rights reserved.

package com.sxj.redis.output;

import com.sxj.redis.codec.RedisCodec;
import com.sxj.redis.protocol.CommandOutput;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link Set} of value output.
 *
 * @param <V> Value type.
 *
 * @author Will Glozer
 */
public class ValueSetOutput<K, V> extends CommandOutput<K, V, Set<V>> {
    public ValueSetOutput(RedisCodec<K, V> codec) {
        super(codec, new HashSet<V>());
    }

    @Override
    public void set(ByteBuffer bytes) {
        output.add(bytes == null ? null : codec.decodeMapValue(bytes));
    }
}
