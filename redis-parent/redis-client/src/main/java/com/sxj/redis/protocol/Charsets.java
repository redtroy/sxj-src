// Copyright (C) 2011 - Will Glozer.  All rights reserved.

package com.sxj.redis.protocol;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * {@link Charset}-related utilities.
 *
 * @author Will Glozer
 */
public class Charsets {
    public static final Charset ASCII = Charset.forName("US-ASCII");

    public static ByteBuffer buffer(String s) {
        return ByteBuffer.wrap(s.getBytes(ASCII));
    }
}
