// Copyright (C) 2011 - Will Glozer.  All rights reserved.

package com.sxj.redis;

/**
 * Exception thrown when the thread executing a redis command is
 * interrupted.
 *
 * @author Will Glozer
 */
@SuppressWarnings("serial")
public class RedisCommandInterruptedException extends RedisException
{
    public RedisCommandInterruptedException(Throwable e)
    {
        super("Command interrupted", e);
    }
}
